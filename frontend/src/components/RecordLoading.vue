<template>
  <v-app class="container" style="margin-top: 70px; margin-bottom: 70px">
    <div class="record-loading">
      <img src="../assets/loading.gif" />
    </div>
    <h2>로딩중입니다.</h2>
  </v-app>
</template>

<script>
import { doSendRecord } from "@/api/record";

export default {
  data() {
    return {
      isLoading: true,
      record: { type: Object },
    };
  },
  async mounted() {
    try {
      // store에 저장한 record 불러오기
      this.record = this.$store.getters.recordData;

      // const type = this.record.blob.type.split("/")[1];
      const today = new Date();
      var year = today.getFullYear();
      var month = ("0" + (today.getMonth() + 1)).slice(-2);
      var day = ("0" + today.getDate()).slice(-2);

      var dateString = year + "-" + month + "-" + day;
      var userSeq = localStorage.getItem("userSeq");

      const link = document.createElement("a");
      link.href = this.record.url;
      // link.download = `${this.filename}.mp3`;

      const formData = new FormData();
      formData.append(`voice_file`, this.record.blob);

      const response = await doSendRecord(formData, dateString, 3);

      if (response.status == 201) {
        this.$router.push(`/result/${response.data.diary_seq}`);
      } else if (response.status == 202) {
        // text가 없는 음성 파일 -> 잘못된 녹음입니다. 제대로 녹음해주세요
        this.$router.push("/record");
        alert("음성 인식이 어렵습니다. 다시 녹음해주세요");
      }

      return response;
    } catch (error) {
      this.$router.push("/record");
      alert("서버 오류 발생.");
      throw error;
    }
  },
};
</script>

<style scoped lang="scss">
img {
  height: 200px;
  margin-top: 150px;
  object-fit: cover;
}
.container {
  &-record-loading {
    position: absolute;
    margin-top: 10px;
    size: 10px;
    width: 20%;
    height: 20%;
    widows: 300px;
    height: 150px;
    object-fit: cover;
  }
}
</style>
