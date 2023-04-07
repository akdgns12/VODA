import os 

import torch 
import numpy as np
from torch.utils.data import Dataset, DataLoader
from .KoBERT.kobert.pytorch_kobert import get_pytorch_kobert_model
from .BERTClassifier import BERTClassifier
from .KoBERT.kobert_hf.kobert_tokenizer import KoBERTTokenizer
from .BERTDataset import BERTDataset

EXPERIMENTS_PATH = os.path.abspath(__file__)[:-26]

class model_call(object):
    def __new__(cls,*args,):
        if not hasattr(cls, "_instance"):
            cls._instance = super().__new__(cls)
            return cls._instance
    def __init__(self,):
            cls = type(self)
            if not hasattr(cls,"_init"):
                self.max_len = 64
                self.batch_size=64
                self.tokenizer = KoBERTTokenizer.from_pretrained('skt/kobert-base-v1')
                self.bertmodel,self.vocab = get_pytorch_kobert_model()
                self.tok=self.tokenizer.tokenize
                self.device = torch.device("cpu")
                # model = get_pytorch_kobert_model()
                self.model = BERTClassifier(self.bertmodel,dr_rate=0.5).to(self.device)
                self.weight = os.path.join(EXPERIMENTS_PATH,"emotion_text/weight/2_acc_7_merge_emotion.pt")
                self.model.load_state_dict(torch.load(self.weight , map_location=self.device))
                self.model.eval()
                cls._init = True

    def predict(self,predict_sentence):
    
        data = [predict_sentence, '0']
        dataset_another = [data]

        another_test = BERTDataset(dataset_another, 0, 1, self.tok, self.vocab, self.max_len, True, False)
        test_dataloader = DataLoader(another_test, batch_size=self.batch_size, num_workers=0)

        # self.model.eval()

        for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
            token_ids = token_ids.long().to(self.device)
            segment_ids = segment_ids.long().to(self.device)

            valid_length= valid_length
            label = label.long().to(self.device)

            out = self.model(token_ids, valid_length, segment_ids)
            test_eval=[]
            for i in out:
                logits=i
                logits = logits.detach().cpu().numpy()
                """
                0 : 슬픔
                1 : 놀람
                2 : 화남
                3 : 중립
                4 : 행복
                """
                if np.argmax(logits) == 0:
                    test_eval.append("슬픔이")
                elif np.argmax(logits) == 1:
                    test_eval.append("놀람이")
                elif np.argmax(logits) == 2:
                    test_eval.append("분노가")
                elif np.argmax(logits) == 3:
                    test_eval.append("중립이")
                elif np.argmax(logits) == 4:
                    test_eval.append("행복이")

            print(f">> \"{predict_sentence}\"에서 " + test_eval[0] + " 느껴집니다." )
            return np.argmax(logits)
