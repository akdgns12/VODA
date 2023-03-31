<template>
  <v-app>
    <v-flex xs12 class="mb-3">
      <v-sheet height="500">
        <vc-calendar
          ref="calendar"
          class="custom-calendar"
          locale="en"
          @update:from-page="handleCalendarChange"
        >
          <template v-slot:day-content="{ day }">
            <div class="flex flex-col h-full z-100 overflow-hidden">
              <div class="day-header">{{ day.day }}</div>
              <v-btn
                v-show="handleEmotionButton(day.day)"
                @click="handleDateClick(day.date)"
              >
                <img
                  v-if="handleEmotionButton(day.day)"
                  :src="
                    require(`@/assets/emotions/${
                      calendarData[day.day - 1].emotionImgUrl
                    }`)
                  "
                  alt="emotion"
                />
              </v-btn>
            </div>
          </template>
        </vc-calendar>
      </v-sheet>
    </v-flex>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    userId: "",
    year: null,
    month: null,
    date: new Date(),
    calendarData: new Array(31).fill({ status: false, emotionImgUrl: "" }),
  }),
  created() {
    this.$store.dispatch("setShowBottomNavigation", true);
    this.$store.dispatch("setShowTopNavigation", true);

    const userData = this.$store.getters.userData;
    this.month = this.date.getMonth() + 1;
    this.year = this.date.getFullYear();
    this.userId = userData.userSeq;
    const formattedDate = `${this.year}-${
      this.month < 10 ? "0" + this.month : this.month
    }-01`;

    this.$store
      .dispatch("getCalendarInfo", {
        userSeq: this.userId,
        date: formattedDate,
      })
      .then(() => {
        const calendarData = this.$store.getters.calendarData;
        const size = Object.keys(calendarData).length;
        for (let i = 0; i < size; i++) {
          const numberStr = calendarData[i].date.split("-")[2];
          const number = Number(numberStr);
          // this.calendarData[number] = true;
          this.calendarData.splice(number - 1, 1, {
            status: true,
            emotionImgUrl: calendarData[i].emotionImgUrl,
          });
        }
      });
    this.$store.dispatch("setShowBottomNavigation", true);
  },
  methods: {
    handleDateClick(date) {
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      const formattedDate = `${year}.${month < 10 ? "0" + month : month}.${
        day < 10 ? "0" + day : day
      }`;
      this.$router.push(`/calendar/diary/${formattedDate}`);
    },
    handleEmotionButton(date) {
      return this.calendarData[date - 1].status;
    },
    handleCalendarChange() {
      this.month = this.$refs.calendar.pages[0].month;
      this.calendarData.fill({ status: false, emotionImgUrl: "" });
      this.year = this.$refs.calendar.pages[0].year;
      const formattedDate = `${this.year}-${
        this.month < 10 ? "0" + this.month : this.month
      }-01`;
      this.$store
        .dispatch("getCalendarInfo", {
          userSeq: this.userId,
          date: formattedDate,
        })
        .then(() => {
          const calendarData = this.$store.getters.calendarData;
          const size = Object.keys(calendarData).length;
          for (let i = 0; i < size; i++) {
            const numberStr = calendarData[i].date.split("-")[2];
            const number = Number(numberStr);
            // this.calendarData[number] = true;
            this.calendarData.splice(number - 1, 1, {
              status: true,
              emotionImgUrl: calendarData[i].emotionImgUrl,
            });
          }
        });
    },
  },
};
</script>

<style scoped lang="scss">
@font-face {
  font-family: "ComingSoon-Regular";
  src: url("../../public/fonts/ComingSoon-Regular.ttf");
}
.my-header {
  align-items: center;
  justify-content: center;
  font-size: 24px;
  padding: 5px 0;
}

@media only screen and (max-width: 600px) {
  .my-header {
    font-size: 32px;
  }
}
::-webkit-scrollbar {
  width: 0px;
}
.day-label {
  font-size: 14px;
}
.day2 {
  margin: 10px;
}

.day-header {
  text-align: center;
  font-size: 18px;
  font-family: "ComingSoon-Regular";
}
::-webkit-scrollbar-track {
  display: none;
}
//달력 테두리
::v-deep .custom-calendar.vc-container {
  //달력 내부 선
  margin-top: 30px;
  // --day-border: 1px solid #b8c2cc;
  // --day-border-highlight: 1px solid #b8c2cc;
  //날짜별 크기
  --day-width: 20px;
  --day-height: 90px;
  //요일칸 색상
  --weekday-bg: #f8fafc;
  //요일 테두리 색상
  --weekday-border: 1px solid #eaeaea;
  .vc-title {
    color: beige;
    font-family: "SigmarOne-Regular";
    font-family: "ComingSoon-Regular";
    font-size: 25px;
  }
  border-radius: 10px 10px 0px 0px;
  //달력 너비
  width: 90%;
  .vc-arrow {
    color: beige;
    background-color: red;
  }
  .vc-header {
    padding: 10px 0;
    background-color: red;
    border-radius: 10px 10px 0px 0px;
  }
  .vc-weeks {
    padding: 0;
  }
  .vc-weekday {
    background-color: var(--weekday-bg);
    border-bottom: var(--weekday-border);
    border-top: var(--weekday-border);
    padding: 5px 0;
    color: black;
    font-family: "ComingSoon-Regular";
  }
  .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: white;

    &.weekday-1,
    &.weekday-7 {
      color: red;
    }

    &:not(.on-bottom) {
      border-bottom: var(--day-border);

      &.weekday-1 {
        border-bottom: var(--day-border-highlight);
      }
    }

    &:not(.on-right) {
      border-right: var(--day-border);
    }
  }

  .vc-day-dots {
    margin-bottom: 5px;
  }
}
</style>
