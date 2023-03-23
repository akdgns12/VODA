<template>
  <v-layout>
    <v-flex xs12 class="mb-3">
      <v-sheet height="500">
        <vc-calendar
          class="custom-calendar"
          dark
          disable-page-swipe
          locale="en"
        >
          <template v-slot:day-content="{ day }">
            <div class="flex flex-col h-full z-100 overflow-hidden">
              <div class="day-header">{{ day.day }}</div>
              <br />
              <v-btn> emotion </v-btn>
            </div>
          </template>
        </vc-calendar>
      </v-sheet>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  components: { BottomNav },
  data: () => ({
    type: "month",
    key: "today",
    focus: new Date(),
    end: "2019-01-06",
    typeOptions: [
      { text: "Day", value: "day" },
      { text: "4 Day", value: "4day" },
      { text: "Week", value: "week" },
      { text: "Month", value: "month" },
      { text: "Custom Daily", value: "custom-daily" },
      { text: "Custom Weekly", value: "custom-weekly" },
    ],
    masks: {
      weekdays: "WWW",
    },
    attributes: [
      {
        key: 1,
        customData: {
          title: "Lunch with mom.",
          class: "bg-red-600 text-white",
        },
      },
    ],
    mediaStream: null,
  }),
  created() {
    this.$store.dispatch("setShowBottomNavigation", true);
  },
  mounted() {
    // this.$refs.calendar.checkChange();
  },
  methods: {
    prev() {
      this.focus = new Date(
        this.focus.getFullYear(),
        this.focus.getMonth() - 1,
        1
      );
    },
    next() {
      this.focus = new Date(
        this.focus.getFullYear(),
        this.focus.getMonth() + 1,
        1
      );
      console.log("focus:", this.focus);
    },
    showEvent(date) {
      console.log(date);
      console.log("focus:", this.focus);
      this.focus = new Date(this.focus);
    },
    customDayFormat(day) {
      return `
          ${day.day}
      `;
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
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
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
