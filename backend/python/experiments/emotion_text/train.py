import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np
from tqdm import tqdm, tqdm_notebook
from .loss import FocalLoss
import pandas as pd

#transformers
from transformers import AdamW
from transformers.optimization import get_cosine_schedule_with_warmup
from transformers import BertModel
from transformers import AutoTokenizer, AutoModelForMaskedLM

from kobert_tokenizer import KoBERTTokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

from sklearn.model_selection import train_test_split

import BERTClassifier
import BERTDataset

#GPU 사용 시
device = torch.device("cuda:0")

EMOTION_LABEL = {
    'happiness':4,
    'surprise':1,
    'sadness':0,
    'angry':2,
    }

def setting_data_label():
    data.rename(columns = {'1번 감정':'감정'}, inplace = True)
    data = data[data['감정'] != "Sadness"]
    data = data[data['감정'] != "sadness"]
    # data2 = data2[data2['감정'] != "Sad"]
    data2 = data2[data2['감정'] != "Anxious"]
    data2 = data2[data2['감정'] != "Hurt"] 
    data2 = data2[data2['감정'] != "Embarrassed"]
    data4 = data4[data4['감정'] != 0] 

    data.loc[(data['감정'] == "Sadness"), '감정'] = 0  
    data.loc[(data['감정'] == "sadness"), '감정'] = 0  
    data.loc[(data['감정'] == "Surprise"), '감정'] = 1  
    data.loc[(data['감정'] == "surprise"), '감정'] = 1  
    data.loc[(data['감정'] == "Angry"), '감정'] = 2 
    data.loc[(data['감정'] == "angry"), '감정'] = 2
    data.loc[(data['감정'] == "Neutral"), '감정'] = 3
    data.loc[(data['감정'] == "neutral"), '감정'] = 3
    data.loc[(data['감정'] == "Happiness"), '감정'] = 4
    data.loc[(data['감정'] == "happiness"), '감정'] = 4
    data.loc[(data['감정'] == "Disgust"), '감정'] = 2
    data.loc[(data['감정'] == "disgust"), '감정'] = 2
    data.loc[(data['감정'] == "Fear"), '감정'] = 1
    data.loc[(data['감정'] == "fear"), '감정'] = 1

    data2.loc[(data2['감정'] == "Sad"), '감정'] = 0
    data2.loc[(data2['감정'] == "Hurt"), '감정'] = 0
    data2.loc[(data2['감정'] == "Embarrassed"), '감정'] = 3
    data2.loc[(data2['감정'] == "Angry"), '감정'] = 2
    data2.loc[(data2['감정'] == "Neutrality"), '감정'] = 3
    data2.loc[(data2['감정'] == "Happy"), '감정'] = 4
    data2.loc[(data2['감정'] == "Anxious"), '감정'] = 0

    data3.loc[(data3['감정'] == "Sadness"), '감정'] = 0  
    data3.loc[(data3['감정'] == "sadness"), '감정'] = 0  
    data3.loc[(data3['감정'] == "Surprise"), '감정'] = 1  
    data3.loc[(data3['감정'] == "surprise"), '감정'] = 1  
    data3.loc[(data3['감정'] == "Angry"), '감정'] = 2 
    data3.loc[(data3['감정'] == "angry"), '감정'] = 2
    data3.loc[(data3['감정'] == "Neutral"), '감정'] = 3
    data3.loc[(data3['감정'] == "neutral"), '감정'] = 3
    data3.loc[(data3['감정'] == "Happiness"), '감정'] = 4
    data3.loc[(data3['감정'] == "happiness"), '감정'] = 4
    # data3.loc[(data3['감정'] == "Disgust"), '감정'] = 2
    # data3.loc[(data3['감정'] == "disgust"), '감정'] = 2
    data3.loc[(data3['감정'] == "Fear"), '감정'] = 1
    data3.loc[(data3['감정'] == "fear"), '감정'] = 1

    data = data[['감정', '발화문']].append(data2[['감정','발화문']]).append(data3[['감정','발화문']])

    data_list = []
    for ques, label in zip(data['발화문'], data['감정'])  :
        data5 = []   
        data5.append(ques)
        data5.append(str(label))
        # print(ques)
        # print(EDA(ques))
    data_list.append(data5)

def count_label():
    # label data type is integer
    emotion_label = {0:'Sad', 1:'Surprise' , 2:'Angry' , 3:'Neutral',4:'Happy'}
    for i in range(5):
      print(f" {emotion_label[i]} : {len(data[data['감정']== i])}")

data = pd.read_csv('emotion_text3.csv', encoding='cp949')
data2 = pd.read_csv('sentiment_classify_2.csv', encoding='utf-8')
data3 = pd.read_csv('sentiment_classify_3.csv', encoding='utf-8')
data4 = pd.read_csv('emotionTraining.csv', encoding='utf-8')

for emotion in set(data4['감정']) : 
  data4.loc[(data4['감정'] == emotion), '감정'] = EMOTION_LABEL[emotion]
  print(emotion)

data_list = [] 
setting_data_label()
print(f"data total size : {len(data_list)}")
count_label()

# kcbert의 tokenizer와 모델을 불러옴.
kcbert_tokenizer = AutoTokenizer.from_pretrained("beomi/kcbert-base")
kcbert = AutoModelForMaskedLM.from_pretrained("beomi/kcbert-base")

# hyperparameter 
max_len = 64
batch_size = 64
warmup_ratio = 0.1
num_epochs = 5
max_grad_norm = 1
log_interval = 200
learning_rate =  5e-5

bertmodel,vocab = get_pytorch_kobert_model()
dataset_train, dataset_test = train_test_split(data_list, test_size=0.1, shuffle=True, random_state=42)
print(f"train dataset size : {len(dataset_train)}")
print(f"test dataset size : {len(dataset_test)}")

tokenizer = KoBERTTokenizer.from_pretrained('skt/kobert-base-v1')
tok=tokenizer.tokenize
data_train = BERTDataset(dataset_train, 0, 1, tok, vocab, max_len, True, False)
data_test = BERTDataset(dataset_test,0, 1, tok, vocab,  max_len, True, False)
train_dataloader = torch.utils.data.DataLoader(data_train, batch_size=batch_size, num_workers=5)
test_dataloader = torch.utils.data.DataLoader(data_test, batch_size=batch_size, num_workers=5)

#BERT 모델 불러오기
model = BERTClassifier(bertmodel,  dr_rate=0.5).to(device)
 
#optimizer와 schedule 설정
no_decay = ['bias', 'LayerNorm.weight']
optimizer_grouped_parameters = [
    {'params': [p for n, p in model.named_parameters() if not any(nd in n for nd in no_decay)], 'weight_decay': 0.01},
    {'params': [p for n, p in model.named_parameters() if any(nd in n for nd in no_decay)], 'weight_decay': 0.0}
]

optimizer = AdamW(optimizer_grouped_parameters, lr=learning_rate)
# loss_fn = nn.CrossEntropyLoss() # 다중분류를 위한 대표적인 loss func
loss_fn = FocalLoss()
t_total = len(train_dataloader) * num_epochs
warmup_step = int(t_total * warmup_ratio)

scheduler = get_cosine_schedule_with_warmup(optimizer, num_warmup_steps=warmup_step, num_training_steps=t_total)

#정확도 측정을 위한 함수 정의
def calc_accuracy(X,Y):
    max_vals, max_indices = torch.max(X, 1)
    train_acc = (max_indices == Y).sum().data.cpu().numpy()/max_indices.size()[0]
    return train_acc
    
train_history=[]
test_history=[]
loss_history=[]
for e in range(num_epochs):
    train_acc = 0.0
    test_acc = 0.0
    model.train()
    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(tqdm_notebook(train_dataloader)):
        optimizer.zero_grad()
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)
        valid_length= valid_length
        label = label.long().to(device)
        out = model(token_ids, valid_length, segment_ids)
         
        #print(label.shape,out.shape)
        loss = loss_fn(out, label)
        loss.backward()
        torch.nn.utils.clip_grad_norm_(model.parameters(), max_grad_norm)
        optimizer.step()
        scheduler.step()  # Update learning rate schedule
        train_acc += calc_accuracy(out, label)
        if batch_id % log_interval == 0:
            print("epoch {} batch id {} loss {} train acc {}".format(e+1, batch_id+1, loss.data.cpu().numpy(), train_acc / (batch_id+1)))
            train_history.append(train_acc / (batch_id+1))
            loss_history.append(loss.data.cpu().numpy())
    print("epoch {} train acc {}".format(e+1, train_acc / (batch_id+1)))
    #train_history.append(train_acc / (batch_id+1))
    
    model.eval()
    for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(tqdm_notebook(test_dataloader)):
        token_ids = token_ids.long().to(device)
        segment_ids = segment_ids.long().to(device)
        valid_length= valid_length
        label = label.long().to(device)
        out = model(token_ids, valid_length, segment_ids)
        test_acc += calc_accuracy(out, label)
    print("epoch {} test acc {}".format(e+1, test_acc / (batch_id+1)))
    test_history.append(test_acc / (batch_id+1))
    if max(test_history) == test_acc/(batch_id+1) : 
      # file name format : {epoch}_acc_{acc}.pt
      torch.save(model,f"./{e}_acc_{int(100*(test_acc/(batch_id+1)))}.pt") 
print(test_history)