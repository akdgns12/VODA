<template>
  <div>
    <table>
      <thead>
        <tr>
          <th>Text</th>
          <th>Emotion</th>
        </tr>
      </thead>
      <tbody>
        
        <tr v-for="diary in diaries.sentenceDtos" :key="diary.id">
          <td>{{ diary.content }}</td>
          <td>{{diary.emotionImagePath}} </td>
          <td><img :src="require(`@/assets/emotions/${diary.emotionImagePath}`)" alt="emotion"></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name : 'TextResult',
  data() {
    return {
      diaries: [],
    };
  },
  mounted() {
    const diarySeq = 57; // 대상 다이어리 시퀀스 값
    fetch(`http://localhost:8080/diary/${diarySeq}`)
      .then((response) => {
        if (response.status!==200) {
          console.log("################################################")
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        console.log(response.status)
        console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        return response.json();
      })
      .then((data) => {
        console.log(data)
        this.diaries = data;
      })
      .catch((error) => {
        console.error(`Fetch error: ${error}`);
      });
  },
};
</script>