import speech_recognition as sr

r = sr.Recognizer()

def stt(filepath):
    test = sr.AudioFile(filepath)
    with test as source:
        audio = r.record(source)
    return r.recognize_google(audio, language='ko-KR')
    # cloud payment needed
    # print(r.recognize_google_cloud(audio, language='ko-KR'), 'google cloud')
    ## pip install botocore, boto3
    ## amazon credential needed
    # print(r.recognize_amazon(audio, region='ko-KR'), 'amazon') 
    ## 결제필요
    # print(r.recognize_bing(audio, language='ko-KR'), 'bing')
    # # houndify 결제필요
    # print(r.recognize_houndify(audio, language='ko-KR'), 'houndify')
    # # key 필요
    # print(r.recognize_ibm(audio, language='ko-KR'), 'ibm')
    # pip install pocketsphinx key 필요
    # print(r.recognize_sphinx(audio, language='ko-KR'), 'sphinx')
    # print(r.recognize_wit(audio), 'wit')
if __name__ == "__main__":
    print(stt('./test/test1.wav'))