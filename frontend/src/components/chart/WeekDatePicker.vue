<template>
  <v-row no-gutters>
    <v-col cols="2">
      <v-btn height="50px"><</v-btn>
    </v-col>
    <v-col cols="8">
      <v-dialog
        ref="modal"
        v-model="modal"
        :close-on-content-click="false"
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
            @blur="date = parseDate(dateFormatted)"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="dates" range></v-date-picker>
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
    dates: ["2023-03-16", "2023-03-22"],
    modal: false,
  }),
  computed: {
    dateRangeText() {
      return this.dates.join(" ~ ");
    },
  },
};
</script>
