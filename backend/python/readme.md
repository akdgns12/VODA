## Package Managing
- use `pip list --format=freeze > requirements.txt` to synchronize with Linux remote environment
- simply `pip install -r requirements.txt` in remote server
- conda list export is uncompetable with Linux environment, needs more research

## Progress
- solved the issue with corrupted data, Training procedure started
- Accuracy is broken, need to find out whyv

## Errors
- WARNING:tensorflow:Your input ran out of data; interrupting training. Make sure that your dataset or generator can generate at least `steps_per_epoch * epochs` batches (in this case, 1299465 batches). You may need to use the repeat() function when building your dataset
- solved: because the size of dataset is None, we have to specify steps per epochs
