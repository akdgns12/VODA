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
                model.add(keras.layers.Dense(1,input_dim=3, activation='relu'))
                self.model = model
                self.model.save("test.h5")   
                print("model saved")
            else:
                pass     
        # if not, use general data
        else:
            self.model=load_model("test.h5")
            print("model loaded")

        self.data = []

    def train(self):
        pass

    def load_data(self,data_path):
        for (root, dirs, files) in os.walk(data_path):
            for file in files:
                filepath = os.path.join(root,file)
                labeldir = root.split('/')[-1].split('_')[0] + '_label'
                labelpath = '/'.join(root.split('/')[:-1] + [labeldir, file.rstrip('.wav')]) + '.json'
                # print(filepath)
                # print(labelpath)
                with open(labelpath, 'r', encoding='UTF8') as label:
                    data = json.load(label)
                emotion = data["화자정보"]["Emotion"]
                self.data.append([filepath, emotion])
        # print(self.data)
        pass

    def plot_data(self):
        for i, (filepath, emotion) in enumerate(self.data):
            audio_array, sampling_rate = librosa.load(filepath)
            fig, ax = plt.subplots()
            librosa.display.waveshow(audio_array, sr=sampling_rate, ax=ax)
            plt.close()
        pass

    def extract_feature(self):
        duration = 7
        for i, (filepath, emotion) in enumerate(self.data):
            audio_array, sampling_rate = librosa.load(filepath, duration=duration)
            d = audio_array.shape[0]
            if d < duration * sampling_rate:
                pad = np.array([0 for _ in range( sampling_rate * duration - d )], float)
            audio_padded = np.hstack((audio_array, pad))
            # print(audio_padded.shape)
            mfcc = (librosa.feature.mfcc(y=audio_padded))
            print(mfcc.shape)

            chroma = (librosa.feature.chroma_stft(y=audio_padded))
            print(chroma.shape)

            mel = librosa.feature.melspectrogram(y=audio_padded)
            print(mel.shape)

            # mel = librosa.feature.melspectrogram()
        pass

test = EmotionRecognizer()
test.load_data('./audio/sample_audio')
# loadtest.plot_data()
test.extract_feature()