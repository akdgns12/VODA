<template>
  <v-card class="diary mx-auto rounded" style="height: 100%">
    <v-card-title class="header pa-3" style="height: 70px">
      <h3 class="text-h4 font-weight-light text-center grow">
        {{ month }} {{ day }} {{ year }}
      </h3>
    </v-card-title>
    <v-row class="container pa-4" justify="space-between">
      <v-col cols="3" class="no-x-scroll">
        <v-sheet
          class="overflow-y-auto overflow-x-hidden"
          style="max-height: calc(100vh - 230px); overflow-y: auto"
        >
          <v-row>
            <v-col v-for="(item, i) in diaryData" :key="i">
              <div @click="selected = item">
                <v-avatar size="50">
                  <img
                    :src="require(`@/assets/emotions/${item.emotionImgUrl}`)"
                    alt="emotion"
                  />
                </v-avatar>
              </div>
            </v-col>
          </v-row>
        </v-sheet>
      </v-col>

      <v-divider vertical></v-divider>

      <v-col class="text-center">
        <v-scroll-y-transition mode="out-in">
          <v-container
            v-if="!selected"
            class="text-h6 grey--text text--lighten-1 font-weight-light"
            style="align-self: center; margin: 0 auto"
            :style="{ maxHeight: 'calc(100vh - 230px)', overflowY: 'auto' }"
          >
            Select a Diary
          </v-container>
          <v-card v-else flat>
            <v-card-text>
              <v-avatar size="88">
                <img
                  :src="require(`@/assets/emotions/${selected.emotionImgUrl}`)"
                  alt="emotion"
                  @click="showResult(selected.diarySeq)"
                />
              </v-avatar>
              <h3 class="text-h5 mb-2">
                {{ selected.emotionName }}
              </h3>
            </v-card-text>
            <v-divider></v-divider>
            <v-row style="margin: 0">
              <v-col class="mb-2" tag="strong">
                {{ selected.content }}
              </v-col>
            </v-row>
            <audio-player :record="selected.voiceUrl" />
            <v-btn color="red" dark @click="showDeletePopup = true">
              Delete
            </v-btn>
          </v-card>
        </v-scroll-y-transition>
      </v-col>
    </v-row>
    <v-dialog v-model="showDeletePopup" max-width="400">
      <v-card>
        <v-card-title class="headline">
          Are you sure you want to delete?
        </v-card-title>
        <v-card-actions>
          <v-btn color="red darken-1" text @click="showDeletePopup = false">
            No
          </v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="deleteSelected(selected.diarySeq)"
          >
            Yes
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import AudioPlayer from "../audio/components/player.vue";
export default {
  components: { AudioPlayer },
  data() {
    return {
      calendarSeq: 0,
      diaryData: new Array(),
      date: "",
      year: "",
      month: "",
      day: "",
      selected: null,
      showDeletePopup: false,
    };
  },
  mounted() {
    this.calendarSeq = this.$route.params.calendarSeq;

    this.$store
      .dispatch("getDiaryData", { calendarSeq: this.calendarSeq })
      .then(() => {
        this.diaryData = this.$store.getters.diaryData;
      });
    this.date = this.$route.query.formattedDate.split(".");
    this.year = this.date[0];
    const monthLabel = [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ];
    this.month = monthLabel[this.date[1] - "0" - 1];
    // this.month[this.date[1]];
    this.day = this.addOrdinalSuffix(this.date[2] - "0");
  },
  methods: {
    addOrdinalSuffix(day) {
      if (day > 3 && day < 21) return day + "th";
      switch (day % 10) {
        case 1:
          return day + "st";
        case 2:
          return day + "nd";
        case 3:
          return day + "rd";
        default:
          return day + "th";
      }
    },
    deleteSelected(diarySeq) {
      this.$store.dispatch("delDiaryData", { diarySeq: diarySeq }).then(() => {
        this.$store
          .dispatch("getDiaryData", { calendarSeq: this.calendarSeq })
          .then(() => {
            this.diaryData = this.$store.getters.diaryData;
            this.selected = null;
          });
      });
      this.showDeletePopup = false;
    },
    showResult(diarySeq) {
      this.$router.push(`/result/${diarySeq}`);
    },
  },
};
</script>

<style>
.header {
  background: linear-gradient(#855cf8, #d3b0ff, #97c7ff);
}
.diary {
  margin-top: 58px;
  height: 100%;
}
.v-expansion-panel-header__icon {
  align-items: center;
  align-content: space-between;
  margin-top: 4px;
  margin-left: 12px;
}
.v-expansion-panel-header {
  width: 80%;
  display: inline;
}
.detail_header {
  align-content: space-between;
  margin-left: auto;
  margin-right: auto;
}
.v-avatar img {
  height: auto;
}
</style>
