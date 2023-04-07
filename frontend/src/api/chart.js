import { apiInstance } from ".";

const api = apiInstance();

async function doGetWeekChart(userSeq, date, success, fail) {
  await api
    .get(`/chart/week/${userSeq}`, { params: { date: date } })
    .then(success)
    .catch(fail);
}

async function doGetMonthChart(userSeq, date, success, fail) {
  await api
    .get(`/chart/month/${userSeq}`, { params: { date: date } })
    .then(success)
    .catch(fail);
}

export { doGetWeekChart, doGetMonthChart };
