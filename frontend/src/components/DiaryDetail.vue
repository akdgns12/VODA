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
          {{ month  }} {{year}}
        </h3>
      </v-card-title>

      <v-img
        src="https://cdn.vuetifyjs.com/images/cards/forest.jpg"
        gradient="to top, rgba(0,0,3,.44), rgba(0,0,0,.44)"
      >
        <v-container>
          <v-row  class = "align-content-right">
            <strong class="text-h1 font-weight-regular mr-6"> {{day-'0'}}</strong>
          </v-row>
        </v-container>
      </v-img>
    </v-card>
    <v-card-text class="py-0">
      <v-timeline
        align-top
        dense
      >
     
        <v-timeline-item
          v-for="(item, index) in diartDatas"
          :key="index"
          :small="item.small"
        > 
          <v-row class="pt-1">
            <v-col cols="3">
              <strong>sda</strong>
            </v-col>
            <v-col>
              <strong>ada</strong>
              <div class="text-caption">asda</div>
            </v-col>
          </v-row>
        </v-timeline-item>
      </v-timeline>
    </v-card-text>
  </v-card>
</template>
<script>
export default {
  data(){
    return{
      year: '',
      month: '',
      day: '',
      date:'',
    }
  },
  methods:{
    splitDate() {
      if(this.$route.params.calendarSeq !== undefined){
        this.date = this.$route.params.calendarSeq.split('.');
        const monthLabel = ["January","February","March","April","May","June","July","August","September","October","November","December"];
   
        this.year = this.date[0];
        this.month = monthLabel[this.date[1] - '0' - 1];
        // this.month[this.date[1]];
        this.day = this.date[2];
      }
 
    },
    // async fetchTimelineData(){
    //   try{
    //     // const response = await axios.get('/diary',{params:{diarySeq: this.$route.params.diarySeq}})
    //     this.timelineData = response.data.timelineData;
    //   }catch(error){
    //     console.error(error);
    //   }
    // }  
  },
  mounted() {
    this.splitDate();
    // this.fetchTimelineData();
  },
}
</script>

<style>

</style>