import { apiInstanceFile } from ".";

const api = apiInstanceFile();

async function doSendRecord(formData, date, text_content, userSeq) {
  try {
    const response = await api.post(`/dairy/`, {
      voice_file: formData.get("voice_file"),
      date: date,
      text_content: text_content,
      id: userSeq,
    });
    return response;
  } catch (error) {
    throw error;
  }
}
export { doSendRecord };
