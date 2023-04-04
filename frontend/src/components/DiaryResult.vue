<template>
  <v-app style="margin-top: 70px; margin-bottom: 70px">
    <v-container style="margin-top: 30px">
      <h2>주요 감정은 '{{ emotionMain }}'입니다.</h2>
      <br />
      <div style="text-align: center">
        <img
          :src="require(`@/assets/emotions/${emotionImagePath}`)"
          style="width: 89px; height: 98px"
        />
      </div>
      <v-col>
        <div class="chart-container">
          <canvas
            ref="bar"
            style="position: relative; z-index: 0; width: 350px"
          ></canvas>
        </div>
      </v-col>

      <li v-for="sentence in sentenceDtos" v-bind:key="sentence">
        <v-row no-gutters style="margin-top: 10px">
          <v-col cols="7" style="text-align: left; margin-left: 32px">
            {{ sentence.content }}
          </v-col>
          <v-col cols="4">
            <img
              :src="require(`@/assets/emotions/${sentence.emotionImagePath}`)"
              style="width: 52px; height: 57px"
            />
          </v-col>
        </v-row>
      </li>
    </v-container>
  </v-app>
</template>

<script>
import axios from "axios";
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);

export default {
  data() {
    return {
      sentenceDtos: [], // Add sentenceDto to the data object
      emotionMain: "",
      emotionImagePath: "",
      type: "bar",
      data: {
        labels: ["슬픔", "놀람", "화남", "중립", "행복"],
        datasets: [
          {
            label: "Dataset 1",
            data: [],
            borderColor: [
              "rgba(54, 162, 235, 1)",
              "rgba(255, 255, 0, 1)",
              "rgba(255, 99, 132, 1)",
              "rgba(255, 165, 0, 1)",
              "rgba(0, 255, 0, 1)",
            ],
            backgroundColor: [
              "rgba(54, 162, 235, 0.2)",
              "rgba(255, 255, 0, 0.2)",
              "rgba(255, 99, 132, 0.2)",
              "rgba(255, 165, 0, 0.2)",
              "rgba(0, 255, 0, 0.2)",
            ],
          },
        ],
      },
    };
  },
  created() {
    this.$store.dispatch("setShowBottomNavigation", true);
    this.$store.dispatch("setShowTopNavigation", true);
    console.log(this.$route.params.diarySeq);
  },
  mounted() {
    this.getData();
  },

  methods: {
    async getData() {
      try {
        const config = {
          headers: { "Content-Type": "application/json" },
        };

        const response = await axios.get(
          `http://localhost:8080/diary/${this.$route.params.diarySeq}`,
          config
        );
        console.log(response);
        const { emotionMain, emotionImagePath, emotionCnt, sentenceDtos } =
          response.data;

        this.data.datasets[0].data = emotionCnt; // Update the data array
        this.sentenceDtos = sentenceDtos; // Update the sentenceDto array
        this.emotionMain = emotionMain;
        this.emotionImagePath = emotionImagePath;
        console.log("aaaaaaaaaaaaaaa", this.emotionMain, this.emtionImagePath);
        this.createChart();
      } catch (error) {
        console.error(error);
      }
    },

    createChart() {
      const canvas = this.$refs.bar;
      if (!canvas) {
        setTimeout(() => this.createChart(), 100);
        return;
      }
      new Chart(canvas, {
        type: "bar",
        data: this.data,
        options: {
          indexAxis: "y",
          elements: {
            bar: {
              borderWidth: 2,
            },
          },
          responsive: true,
          plugins: {
            legend: {
              //position: 'top',
              display: false,
            },
            title: {
              display: true,
              // text: '감정 그래프'
            },
          },
        },
      });
    },
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
