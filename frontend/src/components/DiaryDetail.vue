<template>
  <v-card
    class="mx-auto"
    max-width="400"
  >
    <v-card
      dark
      flat
    >
      <v-btn
        absolute
        bottom
        color="pink"
        right
        fab
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-card-title class="pa-2 purple lighten-3">
        <h3 class="text-h6 font-weight-light text-center grow">
          {{ this.$route.params.date}}
        </h3>
      </v-card-title>

      <v-img
        src="https://cdn.vuetifyjs.com/images/cards/forest.jpg"
        gradient="to top, rgba(0,0,3,.44), rgba(0,0,0,.44)"
      >
        <!-- <v-container class="align-center">
          <v-row align="center">
            <strong class="text-h1 font-weight-regular mr-6"></strong>
          </v-row>
        </v-container> -->
      </v-img>
    </v-card>
    <v-card-text class="py-0">
      <v-timeline
        align-top
        dense
      >
        <v-timeline-item
          v-for="(item, index) in timelineData"
          :key="index"
          :color="item.color"
          :small="item.small"
        >
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>{{ item.time }}</strong>
            </v-col>
            <v-col>
              <strong>{{ item.title }}</strong>
              <div class="text-caption">{{ item.subtitle }}</div>
            </v-col>
          </v-row>
        </v-timeline-item>
      </v-timeline>
    </v-card-text>
  </v-card>
</template>
<script>
import axios from "axios"
export default {
  data(){
    return{
      year: '',
      month: '',
      day: '',
      timelineData:[]
    }
  },
  methods:{
    splitDate() {
      const date = this.$route.params.date.split('.');
      this.year = date[0];
      this.month = date[1];
      this.day = date[2];
    },
    async fetchTimelineData(){
      try{
        const response = await axios.get('/diary',{params:{diarySeq: this.$route.params.diarySeq}})
        this.timelineData = response.data.timelineData;
      }catch(error){
        console.error(error);
      }
    }  
  },
  mounted() {
    this.splitDate();
    this.fetchTimelineData();
  },

}
</script>

<style>

</style>