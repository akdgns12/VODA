<template>
  <v-app>
    <v-container fluid pa-0>
      <v-layout class="d-flex justify-center align-center">
        <v-col cols="auto">
          <v-btn icon @click="prev">
            <v-icon dark>mdi-chevron-left</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="auto">
          <div class="my-header">
            <span>{{ focus.getFullYear() }}년 </span>
            <span>{{ focus.getMonth() + 1 }}월</span>
          </div>
        </v-col>
        <v-col cols="auto">
          <v-btn icon @click="next">
            <v-icon dark>mdi-chevron-right</v-icon>
          </v-btn>
        </v-col>
      </v-layout>
      <v-layout>
        <v-flex xs12 class="mb-3">
          <v-sheet height="500">
            <vc-calendar
              class="custom-calendar max-w-full"
              :attributes="attributes"
              disable-page-swipe
              locale="en"
              color="red"
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

      <v-layout class="bottom-nav">
        <v-bottom-navigation
          v-model="value"
          :background-color="color"
          dark
          class="d-flex justify-between"
          grow
        >
          <v-btn>
            <span></span>
            <v-icon large>
              {{ icons.mdiCalendarMonth }}
            </v-icon>
          </v-btn>

          <v-btn>
            <v-icon color="red" size="60">
              {{ icons.mdiRadioboxMarked }}
            </v-icon>
          </v-btn>

          <v-btn>
            <span></span>
            <v-icon large>
              {{ icons.mdiChartBellCurve }}
            </v-icon>
          </v-btn>
        </v-bottom-navigation>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
import {
  mdiRadioboxMarked,
  mdiChartBellCurve,
  mdiCalendarMonth,
} from "@mdi/js";

export default {
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
    icons: {
      mdiChartBellCurve,
      mdiCalendarMonth,
      mdiRadioboxMarked,
    },
    value: 3,
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
  }),
  mounted() {
    // this.$refs.calendar.checkChange();
  },
  computed: {
    color() {
      switch (this.value) {
        case 0:
          return "#5AC165";
        case 1:
          return "#855CF8";
        case 2:
          return "#FF9500";
        default:
          return "#5AC165";
      }
    },
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
.my-header {
  align-items: center;
  justify-content: center;
  font-size: 24px;
  padding: 5px 0;
}
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
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
.vc-title {
  color: "red";
}
.day-header {
  text-align: center;
  font-size: 15px;
}
::-webkit-scrollbar-track {
  display: none;
}
//달력 테두리
::v-deep .custom-calendar.vc-container {
  //달력 내부 선
  --day-border: 1px solid #b8c2cc;
  --day-border-highlight: 1px solid #b8c2cc;
  //날짜별 크기
  --day-width: 20px;
  --day-height: 90px;
  //요일칸 색상
  --weekday-bg: #f8fafc;
  //요일 테두리 색상
  --weekday-border: 1px solid #eaeaea;

  border-radius: 10px 10px 0px 0px;
  width: 100%;

  .vc-header {
    padding: 10px 0;
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
  }
  .vc-day {
    padding: 0 5px 3px 5px;
    text-align: left;
    height: var(--day-height);
    min-width: var(--day-width);
    background-color: white;

    &.weekday-1,
    &.weekday-7 {
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
