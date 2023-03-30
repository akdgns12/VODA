import { apiInstance } from ".";

const api = apiInstance();

async function doGetCalendar(userSeq, date, success, fail) {
  await api
    .get(`/calendar/${userSeq}`, { params: { date: date } })
    .then(success)
    .catch(fail);
}

export { doGetCalendar };
