<template>
  <div class="spinner-div">
    <div v-if="isLoading">로딩 중입니다.</div>
  </div>
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
      var userSeq = 1;

      const link = document.createElement("a");
      link.href = this.record.url;
      // link.download = `${this.filename}.mp3`;

      const formData = new FormData();
      formData.append(`voice_file`, this.record.blob);

      const response = await doSendRecord(formData, dateString, userSeq);

      if (response.status == 201) {
        this.$router.push(`/result`);
      } else if (response.status == 202) {
        // text가 없는 음성 파일 -> 잘못된 녹음입니다. 제대로 녹음해주세요
        this.$router.push("/record");
      }
      return response;
    } catch (error) {
      throw error;
    }
  },
};
</script>

<style></style>
