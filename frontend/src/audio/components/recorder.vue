<template>
  <v-app
    class="recorder"
    style="width: 100%; margin-top: 70px; margin-bottom: 70px"
  >
    <div style="margin-top: 16px; margin-bottom: 16px">
      <h2>오늘의 일기를</h2>
      <h2>녹음해보세요.</h2>
    </div>
    <v-container class="ar" fluid>
      <v-flex class="ar-content" :class="{ ar__blur: isUploading }">
        <div class="ar-recorder__records-limit" v-if="attempts">
          Attempts: {{ attemptsLeft }}/{{ attempts }}
        </div>
        <div class="ar-recorder__duration">{{ recordedTime }}</div>
        <div class="ar-recorder__time-limit" v-if="time">
          Record duration is limited: {{ time }}m
        </div>
        <v-divider></v-divider>
        <!-- 녹음 파일 선택, 삭제, download component -->
        <div style="max-width: 400px">
          <v-row
            :key="record.id"
            v-for="(record, idx) in recordList"
            style="
              display: flex;
              flex: 1 1 auto;
              justify-content: space-evenly;
              align-items: center;
            "
          >
            <v-col style="color: red" cols="1" @click="removeRecord(idx)"
              >&times;</v-col
            >
            <v-col style="margin-left: 0px">{{ record.duration }}</v-col>
            <v-col>
              <audio-player :record="record" class="audio-player" />
            </v-col>
            <v-col cols="1">
              <downloader class="ar__downloader" :record="record" />
            </v-col>
          </v-row>
        </div>
      </v-flex>
    </v-container>

    <!-- bottom nav -->

    <v-app-bar class="bottom-nav-bar">
      <v-bottom-navigation grow class="bottom-nav">
        <v-btn @click="toCalendar">
          <v-icon large color="black">
            {{ icons.mdiCalendarMonth }}
          </v-icon>
        </v-btn>
        <!-- 녹음 시작 및 중지 버튼 -->
        <v-btn class="ar-recorder">
          <icon-button
            class="ar-icon ar-icon__lg"
            :name="iconButtonType"
            :class="{
              'ar-icon--rec': isRecording,
              'ar-icon--pulse': isRecording && volume > 0.02,
            }"
            @click.native="toggleRecorder"
          />
        </v-btn>
        <v-btn @click="toChart">
          <v-icon large color="black">
            {{ icons.mdiChartBellCurve }}
          </v-icon>
        </v-btn>
      </v-bottom-navigation>
    </v-app-bar>
  </v-app>
</template>

<script>
import AudioPlayer from "./player";
import Downloader from "./downloader";
import IconButton from "./icon-button";
import Recorder from "../library/recorder";
import { convertTimeMMSS } from "../library/utils";
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";

export default {
  created() {
    this.$store.dispatch("setShowTopNavigation", true);
    this.$store.dispatch("setShowBottomNavigation", false);
  },
  props: {
    attempts: { type: Number },
    time: { type: Number },
    bitRate: { type: Number, default: 128 },
    sampleRate: { type: Number, default: 44100 },
    showDownloadButton: { type: Boolean, default: true },
    showUploadButton: { type: Boolean, default: true },
    micFailed: { type: Function },
    beforeRecording: { type: Function },
    pauseRecording: { type: Function },
    afterRecording: { type: Function },
    failedUpload: { type: Function },
    beforeUpload: { type: Function },
    successfulUpload: { type: Function },
    selectRecord: { type: Function },
  },
  data() {
    return {
      isUploading: false,
      recorder: this._initRecorder(),
      recordList: [],
      uploadStatus: null,
      icons: {
        mdiChartBellCurve,
        mdiCalendarMonth,
        mdiRadioboxMarked,
      },
    };
  },

  components: {
    AudioPlayer,
    Downloader,
    IconButton,
  },
  beforeDestroy() {
    this.stopRecorder();
  },
  methods: {
    toCalendar() {
      this.$router.push("/calendar");
    },
    toChart() {
      this.$router.push("/chart");
    },
    toggleRecorder() {
      try {
        if (this.attempts && this.recorder.records.length >= this.attempts) {
          return;
        }
        if (!this.isRecording || (this.isRecording && this.isPause)) {
          this.recorder.start();
        } else {
          this.stopRecorder();
        }
      } catch (error) {
        alert("녹음 길이가 너무 짧습니다.");
        return;
      }
    },
    stopRecorder() {
      if (!this.isRecording) {
        return;
      }
      this.recorder.stop();
      this.recordList = this.recorder.recordList();
    },
    removeRecord(idx) {
      this.recordList.splice(idx, 1);
      this.$set(this.selected, "url", null);
      this.$eventBus.$emit("remove-record");
    },
    choiceRecord(record) {
      if (this.selected === record) {
        return;
      }
      this.selected = record;
      this.selectRecord && this.selectRecord(record);
    },
    _initRecorder() {
      return new Recorder({
        beforeRecording: this.beforeRecording,
        afterRecording: this.afterRecording,
        pauseRecording: this.pauseRecording,
        micFailed: this.micFailed,
        bitRate: this.bitRate,
        sampleRate: this.sampleRate,
        format: this.format,
      });
    },
  },
  computed: {
    attemptsLeft() {
      return this.attempts - this.recordList.length;
    },
    iconButtonType() {
      return this.isRecording && this.isPause
        ? "mic"
        : this.isRecording
        ? "pause"
        : "mic";
    },
    isPause() {
      return this.recorder.isPause;
    },
    isRecording() {
      return this.recorder.isRecording;
    },
    recordedTime() {
      if (this.time && this.recorder.duration >= this.time * 60) {
        this.stopRecorder();
      }
      if (this.time == "00:00") {
        alert("녹음 길이가 너무 짧습니다.");
      }
      return convertTimeMMSS(this.recorder.duration);
    },
    volume() {
      return parseFloat(this.recorder.volume);
    },
  },
};
</script>

<style scoped lang="scss">
.v-bottom-navigation {
  justify-content: space-evenly;
}
.bottom-nav-bar {
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-left: 0px;
  overflow: hidden;
  background-color: white;
  padding: 0px;
}
.bottom-nav {
  position: fixed;
  margin-left: 0px;
}
.ar {
  // width: 420px;
  // height: 420px;
  border-radius: 16px;
  background-color: #ffffff;
  box-shadow: 0 4px 18px 0 rgba(0, 0, 0, 0.17);
  position: relative;
  &-content {
    padding: 16px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  // record List Box
  &-records {
    height: 138px;
    padding-top: 1px;
    overflow-y: auto;
    margin-bottom: 20px;
    margin-right: 20px;
    // record List
    &__record {
      // width: 340px;
      height: 45px;
      padding: 0 3px;
      margin: 0 auto;
      line-height: 45px;
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #ffffff;
      position: relative;
      &--selected {
        border: 1px solid #ffffff;
        border-radius: 24px;
        background-color: #ffffff;
        margin-top: -1px;
        padding: 0 34px;
      }
    }
  }
  &-recorder {
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    &__duration {
      color: #aeaeae;
      font-size: 32px;
      font-weight: 500;
      margin-top: 20px;
      margin-bottom: 16px;
    }
    &__stop {
      position: absolute;
      top: 10px;
      right: -52px;
    }
    &__time-limit {
      position: absolute;
      color: #ffffff;
      font-size: 12px;
      top: 128px;
    }
    &__records-limit {
      position: absolute;
      color: #ffffff;
      font-size: 13px;
      top: 78px;
    }
  }
  &-spinner {
    display: flex;
    height: 30px;
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    margin: auto;
    width: 144px;
    z-index: 10;
    &__dot {
      display: block;
      margin: 0 8px;
      border-radius: 50%;
      width: 30px;
      height: 30px;
      background: #05cbcd;
      animation-name: blink;
      animation-duration: 1.4s;
      animation-iteration-count: infinite;
      animation-fill-mode: both;
      &:nth-child(2) {
        animation-delay: 0.2s;
      }
      &:nth-child(3) {
        animation-delay: 0.4s;
      }
      @keyframes blink {
        0% {
          opacity: 0.2;
        }
        20% {
          opacity: 1;
        }
        100% {
          opacity: 0.2;
        }
      }
    }
  }
  &__text {
    color: rgba(84, 84, 84, 0.5);
    font-size: 16px;
  }
  &__blur {
    filter: blur(2px);
    opacity: 0.7;
  }
  &__overlay {
    position: absolute;
    width: 100%;
    height: 100%;
    z-index: 10;
  }
  &__upload-status {
    text-align: center;
    font-size: 10px;
    padding: 2px;
    letter-spacing: 1px;
    position: absolute;
    bottom: 0;
    &--success {
      color: green;
    }
    &--fail {
      color: red;
    }
  }
  &__rm {
    cursor: pointer;
    position: absolute;
    width: 6px;
    height: 6px;
    padding: 6px;
    line-height: 6px;
    margin: auto;
    left: 10px;
    bottom: 0;
    top: 0;
    color: rgb(244, 120, 90);
  }
  &__downloader,
  &__downloader {
    // right: 115px;
    // margin-top: 10px;
    // margin-right: 10px;
  }
}

.v-application {
  position: fixed;
}

@import "../scss/icons";
</style>
