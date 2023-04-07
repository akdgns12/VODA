import AudioPlayer from "@/audio/components/player";
import AudioRecorder from "@/audio/components/recorder";

Vue.use(AudioRecorder);

const components = {
  AudioPlayer,
  AudioRecorder,

  install(Vue) {
    if (this.installed) {
      return;
    }

    this.installed = true;

    Vue.prototype.$eventBus = Vue.prototype.$eventBus || new Vue();

    Vue.component("audio-player", AudioPlayer);
    Vue.component("audio-recorder", AudioRecorder);
  },
};

export default components;

export { AudioPlayer, AudioRecorder };
