<template>
  <v-row no-gutters>
    <v-col cols="2">
      <v-btn height="50px"></v-btn>
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
            v-model="date"
            append-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="date"
          type="month"
          :active-picker.sync="activePicker"
          :max="
            new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
              .toISOString()
              .substr(0, 10)
          "
          min="1950-01-01"
          @change="save"
        ></v-date-picker>
      </v-dialog>
    </v-col>
    <v-col cols="2">
      <v-btn height="50px">></v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  data: () => ({
    activePicker: null,
    date: "2023-03",
    menu: false,
  }),
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "MONTH"));
    },
  },
  methods: {
    save(date) {
      this.$refs.menu.save(date);
    },
  },
};
</script>
