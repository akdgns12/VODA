import { apiInstanceFile } from ".";

const api = apiInstanceFile();

async function doSendRecord(formData, date, text_content, userSeq) {
  try {
    const response = await api.post(`/dairy/`, {
      formData,
      date,
      text_content,
      userSeq,
    });
    console.log(formData);
    console.log(date);
    console.log(userSeq);
    return response;
  } catch (error) {
    console.log(formData);
    console.log(date);
    console.log(userSeq);
    throw error;
  }
}
export { doSendRecord };
