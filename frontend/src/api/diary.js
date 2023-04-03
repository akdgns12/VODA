import { apiInstance } from ".";

const api = apiInstance();

async function doGetDiary(calendarSeq, success, fail) {
  await api
    .get(`/calendar/diary/${calendarSeq}`, {
      params: { calendarSeq: calendarSeq },
    })
    .then(success)
    .catch(fail);
}

async function doDelDiary(diarySeq, success, fail) {
  console.log(diarySeq);
  await api.delete(`/diary/${diarySeq}`).then(success).catch(fail);
}

export { doGetDiary, doDelDiary };
