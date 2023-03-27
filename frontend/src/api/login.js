import { apiInstance } from ".";

const api = apiInstance();

async function doGetUser( code, res, fail) {
  await api
    .get(`/user/login/oauth/kakao/${code}`, { params: { code : code}},)
    .then((res)=>{
        return res;
    })
    .catch(fail);
}

export { doGetUser };
