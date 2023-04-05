<template>
  <v-card class="diary rounded">
    <v-card-title class="header">
      <h3>{{ month }} {{ day }} {{ year }}</h3>
    </v-card-title>
    <v-row class="container pa-2">
      <v-col cols="2" class="no-x-scroll pa-2">
        <v-sheet
          class="overflow-y-auto overflow-x-hidden"
          style="
            max-height: calc(100vh - 230px);
            overflow-y: auto;
            box-shadow: none;
          "
        >
          <v-row>
            <v-col v-for="(item, i) in diaryData" :key="i">
              <div @click="selected = item">
                <v-avatar size="10vw">
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
          <v-card
            class="diary-detail"
            v-if="selected"
            flat
            v-bind:class="getEmotionClass(selected.emotionImgUrl)"
          >
            <v-card-text>
              <v-col
                style="padding: 3px; display: flex; justify-content: flex-end"
              >
                <v-icon
                  class="mt-2 mr-3"
                  color="red"
                  dark
                  @click="showDeletePopup = true"
                >
                  {{ icon.mdiCloseCircleOutline }}
                </v-icon>
              </v-col>
              <v-avatar size="20vw">
                <img
                  :src="require(`@/assets/emotions/${selected.emotionImgUrl}`)"
                  alt="emotion"
                  @click="showResult(selected.diarySeq)"
                />
              </v-avatar>

              <h3 class="text-h5 mt-3 mb-3">
                {{ selected.emotionName }}
              </h3>
            </v-card-text>
            <v-divider></v-divider>
            <v-row style="margin: 0">
              <v-col class="mb-2 mx-2" tag="strong">
                {{ selected.content }}
              </v-col>
            </v-row>
            <audio-player :src="selected.voiceUrl" />
          </v-card>
        </v-scroll-y-transition>
      </v-col>
    </v-row>
    <v-dialog v-model="showDeletePopup" max-width="400">
      <v-card>
        <v-card-title style="justify-content: center">
          현재 다이어리를 삭제하시겠습니까?
        </v-card-title>
        <v-divider></v-divider>
        <v-card-actions style="justify-content: space-around">
          <v-btn
            style="width: 50%"
            color="blue darken-1"
            text
            @click="showDeletePopup = false"
          >
            취소
          </v-btn>
          <v-divider vertical="true"></v-divider>
          <v-btn
            style="width: 50%"
            color="red darken-1"
            text
            @click="deleteSelected(selected.diarySeq)"
          >
            삭제
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>
<script>
import AudioPlayer from "../audio/components/player.vue";
import { mdiCloseCircleOutline } from "@mdi/js";
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
      icon: {
        mdiCloseCircleOutline,
      },
    };
  },
  mounted() {
    this.calendarSeq = this.$route.params.calendarSeq;

    this.$store
      .dispatch("getDiaryData", { calendarSeq: this.calendarSeq })
      .then(() => {
        this.diaryData = this.$store.getters.diaryData;
        this.selected = this.diaryData[0];
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
    getEmotionClass(emotionUrl) {
      const emotionClasses = {
        "happiness.svg": "bg-happiness",
        "sadness.svg": "bg-sadness",
        "surprise.svg": "bg-surprise",
        "angry.svg": "bg-angry",
        "neutral.svg": "bg-neutral",
      };
      console.log(emotionClasses[emotionUrl]);
      return emotionClasses[emotionUrl] || "";
    },
    deleteSelected(diarySeq) {
      this.$store.dispatch("delDiaryData", { diarySeq: diarySeq }).then(() => {
        this.$store
          .dispatch("getDiaryData", { calendarSeq: this.calendarSeq })
          .then(() => {
            this.diaryData = this.$store.getters.diaryData;
            this.selected = null;
            if (this.diaryData.length === 0) {
              // diaryData가 비어있으면 메인 화면으로 이동
              this.$router.push("/calendar");
            }
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
.diary {
  margin-top: 58px;
  display: grid;
  grid-template-rows: 70px;
  justify-items: center;
  height: calc(100vh - 70px);
}
.header {
  background: linear-gradient(#855cf8, #d3b0ff, #97c7ff);
  width: 100%;
  justify-content: space-around;
  height: 60px;
  align-items: center;
}
.container {
  width: 100%;
  height: calc(100vh - 200px);
}
.diary-detail {
  margin-left: 3px;
  height: 100%;
}
.v-avatar img {
  height: auto;
}

@media only screen and (max-width: 600px) {
}
.bg-happiness {
  background-color: #bde6c1 !important;
}
.bg-sadness {
  background-color: #7ebdff !important;
}
.bg-surprise {
  background-color: #fff1a5 !important;
}
.bg-angry {
  background-color: #f9c4c4 !important;
}
.bg-neutral {
  background-color: #ffcf8c !important;
}
col {
  padding: 4px;
}
</style>
