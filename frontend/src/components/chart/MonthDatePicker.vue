<template>
  <v-row no-gutters>
    <v-col cols="2">
      <v-btn height="50px" @click="before()"><</v-btn>
    </v-col>
    <v-col cols="8">
      <v-dialog
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="dateString"
            append-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="dateString"
          type="month"
          :active-picker.sync="activePicker"
          :max="
            new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
              .toISOString()
              .substr(0, 7)
          "
          min="1950-01-01"
          @change="save"
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
    date: Date,
  },
  data: () => ({
    activePicker: null,
    menu: false,
    dateString: null,
  }),
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "MONTH"));
    },
  },
  methods: {
    save(date) {
      this.date.setMonth(new Date(date).getMonth());
      this.date.setFullYear(new Date(date).getFullYear());
      this.$emit("change", this.date);
      this.$refs.menu.save(date);
    },
    before() {
      this.$emit(
        "change",
        new Date(this.date.setMonth(this.date.getMonth() - 1))
      );
      this.dateString = this.date.toISOString().substring(0, 7);
    },
    after() {
      if (new Date().getMonth() === this.date.getMonth()) {
        alert("이번달까지만 조회 가능합니다");
        return;
      }
      this.$emit(
        "change",
        new Date(this.date.setMonth(this.date.getMonth() + 1))
      );
      this.dateString = this.date.toISOString().substring(0, 7);
    },
  },
  created() {
    this.dateString = this.date.toISOString().substring(0, 7);
  },
};
</script>
