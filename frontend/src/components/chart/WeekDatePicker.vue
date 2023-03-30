<template>
  <v-row no-gutters>
    <v-col cols="2">
      <v-btn height="50px" @click="before()"><</v-btn>
    </v-col>
    <v-col cols="8">
      <v-dialog
        ref="modal"
        v-model="modal"
        :close-on-content-click="true"
        transition="scale-transition"
        offset-y
        max-width="290px"
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="dateRangeText"
            append-icon="mdi-calendar"
            persistent-hint
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="dates"
          range
          :max="
            new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
              .toISOString()
              .substr(0, 10)
          "
          min="1950-01-01"
        ></v-date-picker>
      </v-dialog>
    </v-col>
    <v-col cols="2">
      <v-btn height="50px" @click="after()">></v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    startDate: Date,
    endDate: Date,
  },
  data: () => ({
    dates: [],
    modal: false,
  }),
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
  watch: {
    dates(val) {
      if (val.length == 1) {
        this.endDate.setDate(new Date(val[0]).getDate());
        this.endDate.setMonth(new Date(val[0]).getMonth());
        this.endDate.setFullYear(new Date(val[0]).getFullYear());
        this.startDate.setDate(new Date(val[0]).getDate() - 6);
        this.startDate.setMonth(new Date(val[0]).getMonth());
        this.startDate.setFullYear(new Date(val[0]).getFullYear());
        this.$emit("change", this.startDate, this.endDate);

        this.dates = [];
        this.dates.push(this.startDate.toISOString().substr(0, 10));
        this.dates.push(this.endDate.toISOString().substr(0, 10));
        this.$refs.modal.save(this.dates);
      }
    },
  },
  methods: {
    before() {
      this.$emit(
        "change",
        new Date(this.startDate.setDate(this.endDate.getDate() - 13)),
        new Date(this.endDate.setDate(this.endDate.getDate() - 7))
      );
      this.dates = [];
      this.dates.push(this.startDate.toISOString().substr(0, 10));
      this.dates.push(this.endDate.toISOString().substr(0, 10));
    },
    after() {
      if (
        new Date().getMonth() === this.endDate.getMonth() &&
        new Date().getDate() < this.endDate.getDate() + 7
      ) {
        alert("오늘 날짜까지만 조회 가능합니다");
        this.$emit(
          "change",
          new Date(this.startDate.setDate(new Date().getDate() - 6)),
          new Date(this.endDate.setDate(new Date().getDate()))
        );
        this.dates = [];
        this.dates.push(this.startDate.toISOString().substr(0, 10));
        this.dates.push(this.endDate.toISOString().substr(0, 10));
        return;
      }
      this.$emit(
        "change",
        new Date(this.startDate.setDate(this.endDate.getDate() + 1)),
        new Date(this.endDate.setDate(this.endDate.getDate() + 7))
      );
      this.dates = [];
      this.dates.push(this.startDate.toISOString().substr(0, 10));
      this.dates.push(this.endDate.toISOString().substr(0, 10));
    },
  },
  created() {
    this.dates.push(this.startDate.toISOString().substr(0, 10));
    this.dates.push(this.endDate.toISOString().substr(0, 10));
  },
};
</script>
