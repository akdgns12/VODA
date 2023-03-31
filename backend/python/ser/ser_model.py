import os
import tensorflow as tf
import numpy as np
from keras.models import load_model
import librosa
import gc
import time

os.environ['KMP_DUPLICATE_LIB_OK']='True'

emotions = {"Neutrality" : 0, "Happy" : 1, "Sad" : 2, "Angry" : 3, "Anxious" : 4, "Hurt" : 2, "Embarrassed" : 4}
class EmotionRecognizer:
    def __new__(cls):
        if not hasattr(cls, "_instance"):         
            print("__new__ is called\n")
            cls._instance = super().__new__(cls)
        return cls._instance
    
    def __init__(self):
        cls = type(self)
        if not hasattr(cls, "_init"):             
            print("__init__ is called\n")
            self.model=load_model("sermodel.h5")
            print("standard model loaded")
            cls._init = True
    
    def predict_file(self,filepath):
        if not os.path.isfile(filepath):
            print("file doesn't exist")
            return
        result = []
        ins = self.audio_spectrum(filepath, duration=5)
        for data in ins:
            data = data.reshape((1,128,216,1))
            out = self.model.predict(x=data, batch_size=None)
            result.append(out)
        return result
    
    def audio_spectrum(self, filepath, duration):
        audio_array, sampling_rate = librosa.load(filepath, sr=22050)
        d = audio_array.shape[0]
        p = sampling_rate * duration ## period = 5second
        n = d // p
        result = []
        # if audio file length is shorter than 5s, pad the audio wavefile
        if n < 1:
            pad = p - d
            audio_array = np.pad(audio_array, (0, pad), 'constant', constant_values=0)
            n = 1        
        for i in range(n):
            audio_padded = audio_array[i*p:(i+1)*p]
            
            # normalize is needed because of the data range
            mel = librosa.feature.melspectrogram(y=audio_padded)
            meldb = librosa.power_to_db(mel, ref=np.min)
            melnorm = librosa.util.normalize(meldb)

            data = np.array(melnorm)
            del sampling_rate
            del audio_array
            # del mfcc
            # del chroma
            del mel
            del meldb
            del melnorm
            result.append(data)
            gc.collect()
        return result


# test code
if __name__ == "__main__":
    test = EmotionRecognizer()
    start =time.time()
    emotion = test.predict_file('./test/' + 'test1.wav')
    print(emotion, time.time() -start)
    start2 = time.time()
    emotion = test.predict_file('./test/' + 'test2.wav')
    print(emotion, time.time()-start2)
    start3 =time.time()
    emotion = test.predict_file('./test/' + 'test1.wav')
    print(emotion, time.time() -start3)
