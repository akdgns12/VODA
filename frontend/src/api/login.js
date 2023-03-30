import { apiInstance } from ".";

const api = apiInstance();

async function doGetUser(code) {
  try {
    const response = await api.get(`/user/login/oauth/kakao/${code}`, {
      params: { code: code },
    });
    return response;
  } catch (error) {
    throw error;
  }
}

async function doSignUp(nickName, accessToken) {
  try {
    const response = await api.post(`/user/signup/`, {
      nickName: nickName,
      accessToken: accessToken,
    });
    return response;
  } catch (error) {
    throw error;
  }
}

export { doGetUser, doSignUp };
