import { apiInstance } from ".";

const api = apiInstance();

<<<<<<< HEAD
async function doGetUser(code, res, fail) {
  await api
    .get(`/user/login/oauth/kakao/${code}`, { params: { code: code } })
    .then((res) => {
      return res;
    })
    .catch(fail);
=======
async function doGetUser( code) {
  try {
    const response = await api.get(`/user/login/oauth/kakao/${code}`, {
      params: { code: code },
    });
    return response;
  } catch (error) {
    throw error;
  }
>>>>>>> b72db6e1075f240854e9634cd78eead7fe0b3141
}
export { doGetUser };
