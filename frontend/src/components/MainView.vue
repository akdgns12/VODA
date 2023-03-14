<template>
  <v-layout wrap>

      <v-btn icon @click="$refs.calendar.prev()">
        <v-icon dark>keyboard_arrow_left</v-icon>
      </v-btn>
      <v-toolbar-title v-if ="$refs.calendar">
            {{ $refs.calendar.title }}
      </v-toolbar-title>
      <v-btn icon @click="$refs.calendar.next()">
        <v-icon dark>keyboard_arrow_right</v-icon>
      </v-btn>
    
    <v-flex
      xs12
      class="mb-3"
    >

      <v-sheet height="500">
          <v-calendar
          ref="calendar"
          v-model="start"
          :type="type"
          color="primary"
        ></v-calendar>
      </v-sheet>
    </v-flex>

  </v-layout>
</template>
<script>
  export default {
    data: () => ({
      type: 'month',
      key: 'today',
      dates: new Date(),
      start: "",
      end: '2019-01-06',
      typeOptions: [
        { text: 'Day', value: 'day' },
        { text: '4 Day', value: '4day' },
        { text: 'Week', value: 'week' },
        { text: 'Month', value: 'month' },
        { text: 'Custom Daily', value: 'custom-daily' },
        { text: 'Custom Weekly', value: 'custom-weekly' }
      ]
    })
  }
</script>

<style>

</style>