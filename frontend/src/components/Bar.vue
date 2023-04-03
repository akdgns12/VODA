<template>
    <v-app>
        <v-container style="margin-top: 30px">
                <img :src="require(`@/assets/emotions/${emotion}.png`)" style="width: 50px; height: 50px;">
        </v-container>
        <div > 주요 감정은 '{{emotion}}'입니다. </div>


        
        <v-contatiner >
            <v-col >
                    <div class="chart-container">
                        <canvas ref="bar" style="position: relative; z-index: 0; width: 350px;"></canvas>
                    </div>
            </v-col>
        </v-contatiner>
        
        <h3 style="margin: 15px; font-size: 1.2rem; text-align: center;">문장 분석</h3>
 
        <li v-for="sentence in sentenceDtos" style="border-bottom: 1px solid #eee; padding-bottom: 10px;">
            <div style="margin-top: 10px;">
                <h5> <img :src="require(`@/assets/emotions/${sentence.emotionMain}.png`)" style="width: 15px; height: 15px;">
                 [{{ sentence.emotionMain }}]</h5>
            </div>
            <div>
                <h6>"{{ sentence.content }}"</h6>
            </div>
        </li>
        <br><br>
    </v-app>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

export default {

    data() {
        return {
            diarySeq: 17,
            sentenceDtos: [], // Add sentenceDto to the data object
            emotion: "",
            type: "bar",
            data: {
                labels: ['슬픔', '놀람', '화남', '중립', '행복'],
                datasets: [
                    {
                        label: 'Dataset 1',
                        data: [],
                        borderColor: [
                       
                            'rgba(54, 162, 235, 1)',                     
                            'rgba(255, 255, 0, 1)',
                            'rgba(255, 99, 132, 1)',
                            'rgba(255, 165, 0, 1)',
                            'rgba(0, 255, 0, 1)'
                        ],
                        backgroundColor: [
                         
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 255, 0, 0.2)',
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 165, 0, 0.2)',
                            'rgba(0, 255, 0, 0.2)'
                        ],
                    },
                ],
            },
        }
    },

    mounted() {
        this.getData()
    },

    methods: {
        async getData() {
            try {
                const config = {
                    headers: { 'Content-Type': 'application/json' }
                };
                const response = await axios.get('http://localhost:8080/diary/32', config);
                console.log(response)
                const { emotionMain, emotionCnt, sentenceDtos } = response.data;

                this.data.datasets[0].data = emotionCnt; // Update the data array
                this.sentenceDtos = sentenceDtos; // Update the sentenceDto array

                this.emotion = emotionMain;
                this.createChart();
            } catch (error) {

                console.error(error);
            }
        },

        createChart() {
            const canvas = this.$refs.bar;
            if(!canvas){
                setTimeout(() => this.createChart(), 100);
                return;
            }
            new Chart(canvas, {
                type: 'bar',
                data: this.data,
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
                            display: false
                        },
                        title: {
                            display: true,
                            // text: '감정 그래프'
                        },
                    }
                },
            })
        }
    },
};
</script>
<style>
.chart-container {
    position: relative;
    width: 100%;
    height: auto;
}

li {
  list-style: none;
  padding: 0;
  margin: 0;
}
</style>

