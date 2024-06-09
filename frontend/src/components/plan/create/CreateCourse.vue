<template>
    <div class="row mt-3 position-relative" style="margin-bottom: 100px;">
        <div class="col-md-12">
            <KakaoMap ref="mapComponent" />
        </div>
        <div class="col-md-3 plan-overlay" @drop="handleDrop" @dragover="handleDragOver">
            <div class="mb-2 text-center">나만의 여행코스를 만들어보세요</div>
            <table class="planTable">
                <tr v-for="(place, index) in places" :key="index" style="height: 30px;">
                    <td class="indexes">
                        <span class="index">{{ index + 1 }}</span>
                    </td>
                    <td class="title">
                        {{ place.title }} <span class="area-tag">{{ place.contentType }}</span>
                    </td>
                    <td class="options">
                        <img src="https://velog.velcdn.com/images/gangintheremark/post/1159b723-b67e-4392-a78d-cd5b7aed7c62/image.png"
                            width=18 class="pointer" @click="up(index)">
                        <img src="https://velog.velcdn.com/images/gangintheremark/post/b50ee6f2-fcee-446b-bbed-a02fe6b56be3/image.png"
                            width=18 class="pointer mx-1" @click="down(index)">
                        <img src="https://velog.velcdn.com/images/gangintheremark/post/e6370c0c-4f44-4a66-b7e3-a7f6e8887d7b/image.png"
                            width=20 class="pointer" @click="deleteContent(index)">
                    </td>
                </tr>
            </table>
            <div class="mt-3 text-center">총 코스거리 : {{ totalDist }} km</div>
            <div class="mt-3 text-center" v-if="places.length">
                <button type="button" class="btn mx-2" data-bs-toggle="modal" data-bs-target="#saveModal">
                    저장
                </button>
            </div>
        </div>
        <div class="modal fade" id="saveModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <input type="text" class="form-control" placeholder="코스명을 입력하세요" v-model="title">
                        <select id="sido" class="form-select mt-2" name="sido" @change="getGugun" v-model="sido">
                            <option value="0">지역을 선택하세요</option>
                            <option v-for="sido in sidoState.sidoList" :key="sido.sido_code" :value="sido.sido_code">
                                {{ sido.sido_name }}
                            </option>
                        </select>
                        <div class="text-center">
                            <div class="mt-4">🚗 나의 여행코스 🚗</div>
                            <ul class=" mt-3">
                                <li v-for="(attraction, index) in places" :key="attraction.contentId">
                                    <div><span>{{ index + 1 }}. </span> {{ attraction.title }} <span
                                            class="area-tag mx-1">{{
                                                attraction.contentType }}</span></div>
                                    <div class="gray">{{ attraction.addr }}</div>
                                </li>
                            </ul>
                            <div class="mt-3">총 코스거리 : {{ totalDist }} km</div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn" id="cancel-btn" data-bs-dismiss="modal">취소</button>
                        <button type="button" class="btn" @click="create">저장</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script setup>
import KakaoMap from '@/components/map/KakaoMap.vue';
import { ref, inject, watchEffect } from 'vue'
import { defineProps } from 'vue';
import { createPlan } from '@/api/plan'

const props = defineProps({
    places: {
        type: Array,
        required: true
    }
});

watchEffect(() => {
    positions = []
    placesId = []
    props.places.forEach(newPlace => {
        positions.push(newPlace);
        placesId.push(newPlace.contentId)
        totalDist = mapComponent.value.displayMarkerWithPolyline(positions, markers, polylines, overlays);
    });

});

const sidoState = inject('sidoState');
const userState = inject('userState');
const userInfo = userState.userInfo;
const mapComponent = ref('')
const sido = ref('0')
const title = ref('')
var placesId = []
var totalDist = ref('0')
var markers = []
var polylines = []
var overlays = []
var positions = []

const create = () => {
    console.log(placesId)
    createPlan(userInfo.userId, title.value, totalDist, sido.value, placesId);
}

const up = (index) => {
    if (index > 0) {
        const tempPlace = props.places[index];
        props.places.splice(index, 1);
        props.places.splice(index - 1, 0, tempPlace);
    }
}

const down = (index) => {
    if (index < props.places.length - 1) {
        const tempPlace = props.places[index];
        props.places.splice(index, 1);
        props.places.splice(index + 1, 0, tempPlace);
    }
}

const deleteContent = (index) => {
    props.places.splice(index, 1);
}


</script>

<style scoped>
#map {
    height: 600px;
}

.row {
    flex-wrap: nowrap;
}

.index {
    background-color: rgb(130, 203, 196);
    color: white;
    padding: 3px 8px;
    border-radius: 30px;
}

.indexes {
    width: 10%;
}

.title {
    width: 60%;
}

.options {
    width: 22%;
    text-align: center;
}

.plan-overlay {
    position: absolute;
    top: 20px;
    left: 20px;
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 5px;
    padding: 10px;
    z-index: 10;
    max-height: 600px;
    overflow-y: auto;
}
</style>