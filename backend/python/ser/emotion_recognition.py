import os
import sys
import tensorflow as tf
import numpy as np
import pandas as pd
import random
import keras
from keras.models import load_model
import json
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
import librosa
import gc

os.environ['KMP_DUPLICATE_LIB_OK']='True'

emotions = {"Neutrality" : 7, "Happy" : 1, "Sad" : 2, "Angry" : 3, "Anxious" : 4, "Hurt" : 5, "Embarrassed" : 6}

class EmotionRecognizer:

    def __init__(self, name=None):
        
        # if specific model for the individual has been given
        if name:
            # if you want to make new model
            if name == "new":
                model = keras.models.Sequential()
                model.add(keras.layers.Conv2D(32, (3, 3), activation='relu', input_shape=(128, 302, 3)))
                model.add(keras.layers.MaxPooling2D((2, 2)))
                model.add(keras.layers.Conv2D(64, (3, 3), activation='relu'))
                model.add(keras.layers.MaxPooling2D((2, 2)))
                # model.add(keras.layers.Dropout(0.2))
                model.add(keras.layers.Conv2D(64, (3, 3), activation='relu'))
                model.add(keras.layers.MaxPooling2D((2, 2)))
                model.add(keras.layers.Dropout(0.3))
                model.add(keras.layers.Flatten())
                model.add(keras.layers.Dense(1024, activation='relu'))
                model.add(keras.layers.Dense(30, activation='relu'))
                model.add(keras.layers.Dropout(0.3))
                model.add(keras.layers.Dense(7, activation='softmax'))
                print(model.summary())
                self.model = model
                self.model.save("test.h5")   
                print("model saved")
            else:
                pass     
        # if not, use general data
        else:
            self.model=load_model("test.h5")
            print("standard model loaded")

        self.data = []
        self.data_loaded = False

    def save_model(self):
        self.model.save("test.h5")
        print("model saved")

    def train(self, optimizer="adam", 
              loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False), 
              metrics=['accuracy'],
              batch_size=10, epochs=10
              ):
        if not self.data_loaded:
            print("data not loaded")
            return
        self.model.compile(optimizer=optimizer, loss=loss, metrics=metrics)
        dataset = self.dataset.batch(batch_size)
        history = self.model.fit(dataset, epochs=epochs)
        pass

    def validate(self, batch_size=10,
                optimizer="adam", 
                loss=tf.keras.losses.CategoricalCrossentropy(from_logits=False), 
                metrics=['accuracy'],
                ):
        if not self.data_loaded:
            print("data not loaded")
            return
        self.model.compile(optimizer=optimizer, loss=loss, metrics=metrics)
        dataset = self.dataset.batch(batch_size)
        self.model.evaluate(dataset)

    def predict_dataset(self):
        result = self.model.predict(self.dataset.batch(10))
        return result
    
    def predict_file(self,filepath):
        if not os.path.isfile(filepath):
            print("file doesn't exist")
            return
        data = np.array([self.audio_spectrum(filepath, duration=7)])
        print(data.shape)
        result = self.model.predict(x=data, batch_size=None)
        return result

    def load_data(self,data_path):
        dirpath = './dataset/'+ data_path.split('/')[-2] + '/' + data_path.split('/')[-1]
        if os.path.isdir(dirpath):
            self.dataset = tf.data.Dataset.load(dirpath)
            print('current dataset:', dirpath)
            self.data_loaded = True
            return
        
        print("dataset not found. start to search for metadata")
        self.data = []
        for (root, dirs, files) in os.walk(data_path):
            for file in files:
                filepath = os.path.join(root,file)
                # labeldir = data_path + '_label'
                labelpath = filepath.replace('audio', 'label').replace('wav', 'json')
                # labelpath = '/'.join(root.split('/')[:-1] + [labeldir, file.rstrip('.wav')]) + '.json'
                # print(filepath, labelpath)
                with open(labelpath, 'r', encoding='UTF8') as label:
                    data = json.load(label)
                emotion = data["화자정보"]["Emotion"]
                n = tf.one_hot(emotions[emotion], 7)
                self.data.append([filepath, n])
        self.extract_feature(data_path)
        pass

    def unload_data(self):
        del self.data
        gc.collect()
        self.data = []
        self.dataset = None
        self.data_loaded = False
        print("data unloaded")

    def plot_data(self):
        for i, (filepath, emotion) in enumerate(self.data):
            audio_array, sampling_rate = librosa.load(filepath)
            fig, ax = plt.subplots()
            librosa.display.waveshow(audio_array, sr=sampling_rate, ax=ax)
            plt.close()
        pass

    def audio_spectrum(self, filepath, duration):
        audio_array, sampling_rate = librosa.load(filepath, duration=duration)
        d = audio_array.shape[0]
        pad = sampling_rate * duration - d
        if pad > 0:
            audio_padded = np.pad(audio_array, (pad // 2, pad - pad // 2), 'constant', constant_values=0)
        else:
            audio_padded = audio_array 
        
        # extract spectral feature by three different method
        # normalize is needed because of the data range
        mfcc = (librosa.feature.mfcc(y=audio_padded, n_mfcc=128, norm="ortho"))

        chroma = (librosa.feature.chroma_stft(y=audio_padded, n_chroma=128))

        mel = librosa.feature.melspectrogram(y=audio_padded)
        meldb = librosa.power_to_db(mel, ref=np.min)
        melnorm = librosa.util.normalize(meldb)

        data = np.stack((mfcc,chroma, melnorm), axis=2)
        del sampling_rate
        del audio_array
        del mfcc
        del chroma
        del mel
        del meldb
        del melnorm
        gc.collect()
        return data

    def extract_feature(self, data_path):
        print("feature extracting...")
        duration = 7
        result = []
        labels = []
        l = int(len(self.data) / 10)
        for i, (filepath, emotion) in enumerate(self.data):
            if i % l == 0:
                print(f'{10*i/l} % completed')
            #convert audio to spectral image data
            data = self.audio_spectrum(filepath, duration)

            result.append(data)
            labels.append(emotion)

        result = np.array(result)
        labels = np.array(labels)
        resulttensor = tf.convert_to_tensor(result, dtype=tf.float32)
        labeltensor = tf.convert_to_tensor(labels, dtype=tf.float32)
        print("input data shape: ", resulttensor.shape)
        print("label data shape:", labeltensor.shape)
        dataset = tf.data.Dataset.from_tensor_slices((resulttensor, labeltensor))
        tf.data.Dataset.save(dataset, path='./dataset/'+ data_path.split('/')[-2] + '/' + data_path.split('/')[-1])
        self.dataset = dataset
        self.data_loaded = True
        print("current dataset: ", data_path.split('/')[-1])
        del dataset
        del result
        del labels
        del resulttensor
        del labeltensor
        del data
        pass

# test code
if __name__ == "__main__":
    test = EmotionRecognizer()
    for dirs in os.listdir('./data/Training/T2_audio/감정/당황'):
        test.load_data('./data/Training/T2_audio/감정/당황/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T2_audio/감정/상처'):
        test.load_data('./data/Training/T2_audio/감정/상처/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T2_audio/감정/중립'):
        test.load_data('./data/Training/T2_audio/감정/중립/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/기쁨구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/기쁨구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/기쁨대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/기쁨구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/당황구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/당황구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/당황대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/당황대화체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/분노구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/분노구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/분노대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/분노대화체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/불안구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/불안구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/불안대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/불안대화체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/상처구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/상처구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/상처대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/상처대화체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/슬픔구연체'):
        test.load_data('./data/Training/T4_audio/감정발화/슬픔구연체/' + dirs)
        test.unload_data()
        gc.collect()
    for dirs in os.listdir('./data/Training/T4_audio/감정발화/슬픔대화체'):
        test.load_data('./data/Training/T4_audio/감정발화/슬픔대화체/' + dirs)
        test.unload_data()
        gc.collect()
    # result = test.predict_file('./audio/sample_audio/0018_G2A3E4S0C0_JBR_000001.wav')
    # print(result)