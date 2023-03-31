<template>

    <v-app>
        <br><br><br>
        <!-- <div><img :src="require(`@/assets/emotions/${emotion}.png`)" alt="emotion" style="width:10%; height: auto;"></div> -->
        
      <div>your emotion is '{{emotion}}' </div>

      <br>
      <!-- <div> -->
      <!-- <v-container> -->
        <!-- <canvas ref="bar"/> -->
        <!-- <canvas ref="bar" style="display: block" width="100%" height="300"></canvas> -->
      <!-- </v-container> -->
      <!-- </div> -->
    <div>
        <div class="image-container">
            <img :src="require(`@/assets/emotions/${emotion}.png`)" alt="emotion" />
        </div>
        <div class="chart-container">
          <canvas ref="bar"></canvas>
        </div>
    </div>

      <!-- <v-container fluid class="pa-0">  
        <canvas ref="bar"/>
      </v-container> -->
      <li v-for="sentence in sentenceDtos">
        <div>{{ sentence.content }}</div>
        <div>{{ sentence.emotionMain }}</div>
     </li>
      <br><br><br>
    </v-app>
</template>
  
<script>
  import axios from "axios";
  import { Chart, registerables} from "chart.js";
  Chart.register(...registerables);

  export default {

    data() {
        return {
            diarySeq:57,
            sentenceDtos: [], // Add sentenceDto to the data object
            emotion:"",
            type: "bar",
            data: {
                labels: ['슬픔', '놀람', '화남', '중립', '행복'],
                datasets: [
                    {
                        label: 'Dataset 1',
                        data: [],
                        borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)'
                        ],
                        backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)'
                        ],
                    },
                ],
            },
        }
    },

    mounted(){
        this.getData()
    },

    methods:{
        async getData() {
            try {
                const config = {
                headers: { 'Content-Type': 'application/json' }
                };
                const response = await axios.get('http://localhost:8080/diary/37', config);
                console.log(response)
                const {emotionMain, emotionCnt, sentenceDtos} = response.data;
      
                this.data.datasets[0].data = emotionCnt; // Update the data array
                this.sentenceDtos = sentenceDtos; // Update the sentenceDto array
      
                this.emotion = emotionMain; 
                this.createChart();
            } catch (error) {
                console.error(error);
            }
        },

        createChart(){
             new Chart(this.$refs.bar,{
                type:'bar',
                data:this.data,
                options: {
                   indexAxis: 'y',       
                   elements: {
                    bar: {
                        borderWidth: 2,
                    }
                    },
                    responsive: true,
                    plugins: {
                        legend: {
                            //position: 'top',
                            display: true
                        },
                        title: {
                            display: true,
                            text: '감정 그래프'
                        }, 
                    }
                },                
            })
        }
    },
  };
</script>
<style>
.image-container {
  position: relative;
  z-index: 1;
}

.chart-container {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  color: black;
}
</style>

