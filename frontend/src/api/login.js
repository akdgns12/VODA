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
export { doGetUser };
