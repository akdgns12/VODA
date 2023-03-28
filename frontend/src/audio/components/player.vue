<template>
  <div class="ar-player">
    <div class="ar-player-actions">
      <v-bottom-sheet inset>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            id="play"
            class="ar-icon ar-icon__lg ar-player__play"
            :name="playBtnIcon"
            :class="{ 'ar-player__play--active': isPlaying }"
            @click.native="playback"
            v-bind="attrs"
            v-on="on"
          />
        </template>
        <v-card tile>
          <v-progress-linear>
            <line-control
              class="ar-player__progress"
              ref-id="progress"
              :percentage="progress"
              @change-linehead="_onUpdateProgress"
            />
          </v-progress-linear>
          <v-list>
            <v-list-item>
              <v-list-item-content>
                <v-col align="left">{{ playedTime }}</v-col>
              </v-list-item-content>
              <v-list-item-content>
                <v-col align="right">{{ duration }}</v-col>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-list>
            <!-- <v-list-item-content>
                <v-list-item-title>The Walker</v-list-item-title>
                <v-list-item-subtitle
                  >Fitz & The Trantrums</v-list-item-subtitle
                >
              </v-list-item-content> -->
            <!-- </v-list-item> -->

            <v-spacer></v-spacer>
            <v-list-item>
              <v-row justify="end">
                <v-col cols="5">
                  <v-row>
                    <v-btn icon @click="_rewindProgress()">
                      <v-icon>mdi-rewind</v-icon>
                    </v-btn>

                    <icon-button
                      id="play"
                      class="ar-icon ar-icon__lg ar-player__play"
                      :name="playBtnIcon"
                      :class="{ 'ar-player__play--active': isPlaying }"
                      @click.native="playback"
                    />

                    <v-btn icon @click="_postwindProgress()">
                      <v-icon>mdi-fast-forward</v-icon>
                    </v-btn>
                  </v-row>
                </v-col>

                <v-col cols="3">
                  <volume-control @change-volume="_onChangeVolume" />
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
        </v-card>
      </v-bottom-sheet>
    </div>
    <audio :id="playerUniqId" :src="audioSource"></audio>
  </div>
</template>

<script>
import IconButton from "../components/icon-button.vue";
import LineControl from "../components/line-control.vue";
import VolumeControl from "../components/volume-control.vue";
import { convertTimeMMSS } from "../library/utils";

export default {
  props: {
    src: { type: String },
    record: { type: Object },
    filename: { type: String },
  },
  data() {
    return {
      isPlaying: false,
      duration: convertTimeMMSS(0),
      playedTime: convertTimeMMSS(0),
      progress: 0,
    };
  },
  components: {
    IconButton,
    LineControl,
    VolumeControl,
  },
  mounted: function () {
    this.player = document.getElementById(this.playerUniqId);
    this.player.addEventListener("ended", () => {
      this.isPlaying = false;
    });
    this.player.addEventListener("loadeddata", (ev) => {
      this._resetProgress();
      this.duration = convertTimeMMSS(this.player.duration);
    });
    this.player.addEventListener("timeupdate", this._onTimeUpdate);
    // this.$eventBus.$on("remove-record", () => {
    //   this._resetProgress();
    // });
  },
  computed: {
    audioSource() {
      const url = this.src || this.record.url;
      if (url) {
        return url;
      } else {
        this._resetProgress();
      }
    },
    playBtnIcon() {
      return this.isPlaying ? "pause" : "play";
    },
    playerUniqId() {
      return `audio-player${this._uid}`;
    },
  },
  methods: {
    playback() {
      if (!this.audioSource) {
        return;
      }
      if (this.isPlaying) {
        this.player.pause();
      } else {
        setTimeout(() => {
          this.player.play();
        }, 0);
      }
      this.isPlaying = !this.isPlaying;
    },
    // 뒤로가기 버튼
    _rewindProgress() {
      this.player.currentTime = 0;
    },
    // 앞으로가기 버튼
    _postwindProgress() {
      // 0.5초 앞으로 -> 일정시간 이상 되면 녹음 끝으로 이동하는 문제
      if (this.isPlaying) {
        this.player.currentTime += 2;
        this._onTimeUpdate();
      } else {
        this.player.currentTime += 2;
        this._onTimeUpdate();
      }

      console.log(this.player.currentTime);
      console.log(this.playedTime);
      console.log(this.progress);
    },
    _resetProgress() {
      if (this.isPlaying) {
        this.player.pause();
      }

      this.duration = convertTimeMMSS(0);
      this.playedTime = convertTimeMMSS(0);
      this.progress = 0;
      this.isPlaying = false;
    },
    _onTimeUpdate() {
      this.playedTime = convertTimeMMSS(this.player.currentTime);
      this.progress = (this.player.currentTime / this.player.duration) * 100;
    },
    _onUpdateProgress(pos) {
      if (pos) {
        this.player.currentTime = pos * this.player.duration;
      }
    },
    _onChangeVolume(val) {
      if (val) {
        this.player.volume = val;
      }
    },
  },
};
</script>

<style lang="scss">
.ar-player {
  align-self: right;
}
.ar-player {
  width: 380px;
  height: unset;
  border: 0;
  border-radius: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  background-color: unset;
  font-family: "Roboto", sans-serif;
  & > .ar-player-bar {
    border: 1px solid #e8e8e8;
    border-radius: 24px;
    margin: 0 0 0 5px;
    & > .ar-player__progress {
      width: 125px;
    }
  }
  &-bar {
    display: flex;
    align-items: center;
    height: 38px;
    padding: 0 12px;
    margin: 0 5px;
  }
  &-actions {
    width: 55%;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
  &__progress {
    position: absolute;
    left: 0;
    width: 100%;
    // width: 600px;
    // margin: 0 8px;
  }
  &__time {
    color: rgba(84, 84, 84, 0.5);
    font-size: 16px;
    width: 41px;
  }
  &__play {
    width: 45px;
    height: 45px;
    background-color: #ffffff;
    box-shadow: 0 2px 11px 11px rgba(0, 0, 0, 0.07);
    &--active {
      fill: white !important;
      background-color: #05cbcd !important;
      &:hover {
        fill: #505050 !important;
      }
    }
  }
}
@import "../scss/icons";
</style>
