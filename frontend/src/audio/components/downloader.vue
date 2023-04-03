<style lang="scss">
@import "../scss/icons";
</style>

<template>
  <icon-button
    id="submit"
    class="ar-icon ar-icon__xs ar-icon--no-border"
    name="download"
    @click.native="submit"
  />
</template>

<script>
import IconButton from "./icon-button";
import { doSendRecord } from "@/api/record";

export default {
  props: {
    record: { type: Object },
    filename: { type: String },
  },
  components: {
    IconButton,
  },
  methods: {
    async submit() {
      if (!this.record.url) {
        return;
      }

      try {
        const formData = new FormData();
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

        formData.append(`voice_file`, this.record.blob);

        const response = await doSendRecord(formData, dateString, userSeq);
        return response;
      } catch (error) {
        throw error;
      }
    },
    download() {
      if (!this.record.url) {
        return;
      }
      const type = this.record.blob.type.split("/")[1];
      const link = document.createElement("a");
      link.href = this.record.url;
      link.download = `${this.filename}.${type}`;
      // link.download = `${this.filename}.mp3`;
      link.click();
    },
  },
};
</script>
