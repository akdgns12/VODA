import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    withCredentials: true,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });

  return instance;
}

function apiInstanceFile() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_PAPI_BASE_URL,
    withCredentials: true,
    headers: {
      "Content-type": "multipart/form-data",
    },
  });

  return instance;
}

export { apiInstance, apiInstanceFile };
