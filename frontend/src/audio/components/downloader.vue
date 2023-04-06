<style lang="scss">
@import "../scss/icons";
</style>

<template>
  <!-- <icon-button
    id="submit"
    class="ar-icon ar-icon__xs ar-icon--no-border"
    name="download"
    @click.native="submit"
  /> -->
  <v-btn
    icon
    class="btn-icon"
    id="submit"
    name="download"
    @click.native="submit"
  >
    <v-icon>mdi-file-send</v-icon>
  </v-btn>
</template>

<script>
import IconButton from "./icon-button";

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

      // store에 record 저장하고 로딩페이지로 라우팅
      this.$store.dispatch("setRecord", { data: this.record });
      this.$router.push(`/recordLoading`);
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
<style scoped lang="scss">
.btn-icon {
  // margin-left: 200px;
}
</style>
