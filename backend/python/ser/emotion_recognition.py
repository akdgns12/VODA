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

os.environ['KMP_DUPLICATE_LIB_OK']='True'

emotions = ["neutral", "happiness", "sadness", "angry", "disgust", "surprise", "fear"]

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
        dirpath = './dataset/' + data_path.split('/')[-1]
        if os.path.isdir(dirpath):
            self.dataset = tf.data.Dataset.load(dirpath)
            print('current dataset:', dirpath)
            self.data_loaded = True
            return
        
        print("dataset not found. start to search for metadata")
        for (root, dirs, files) in os.walk(data_path+'_audio'):
            for file in files:
                filepath = os.path.join(root,file)
                labeldir = root.split('/')[-1].split('_')[0] + '_label'
                labelpath = '/'.join(root.split('/')[:-1] + [labeldir, file.rstrip('.wav')]) + '.json'
                with open(labelpath, 'r', encoding='UTF8') as label:
                    data = json.load(label)
                emotion = data["화자정보"]["Emotion"]
                if emotion == "Anxious":
                    n = tf.one_hot(0, 7)
                self.data.append([filepath, n])
        self.extract_feature(data_path)
        pass

    def unload_data(self):
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
        return data

    def extract_feature(self, data_path):
        print("feature extracting...")
        duration = 7
        result = []
        labels = []
        for i, (filepath, emotion) in enumerate(self.data):

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
        tf.data.Dataset.save(dataset, path='./dataset/' + data_path.split('/')[-1])
        self.dataset = dataset
        self.data_loaded = True
        print("current dataset: ", data_path.split('/')[-1])
        pass

# test code
if __name__ == "__main__":
    test = EmotionRecognizer()
    test.load_data('./audio/sample')
    # loadtest.plot_data()
    # test.extract_feature()
    # test.train()
    # test.save_model()
    # test.validate()
    # result = test.predict_dataset()
    # print(result[:10])
    result = test.predict_file('./audio/sample_audio/0018_G2A3E4S0C0_JBR_000001.wav')
    print(result)
