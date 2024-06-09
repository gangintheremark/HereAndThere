<script setup>
import CreateCourse from "@/components/plan/create/CreateCourse.vue"
import SelectPlace from "@/components/plan/create/SelectPlace.vue"
import { ref } from "vue";

const selectedPlaces = ref([])

const updateSelectedPlaces = (places) => {
    selectedPlaces.value = places;
}

</script>

<template>
    <SelectPlace @update-places="updateSelectedPlaces" />
    <hr class="my-5">
    <div id="create-course">
        <CreateCourse :places="selectedPlaces" />
    </div>
</template>

<style scoped></style>