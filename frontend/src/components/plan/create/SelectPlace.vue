<script setup>
import { onMounted, ref, inject, watch } from "vue";
import axios from "axios";
import KakaoMap from "@/components/map/KakaoMap.vue";
import { searchAttraction } from "@/api/attraction.js";


const sidoState = inject('sidoState');
const gugunList = ref([])
const contentList = ref([])
const areas = ref([])

const sido = ref('0')
const gugun = ref('0')
const type = ref('0')
const keyword = ref('')
const msg = ref('')

// kakaoMap
const emit = defineEmits(['update-places'])
const selectedPlaces = ref([])
const mapComponent = ref('')
var markers = []
var positions = []


onMounted(() => {
    /* TODO : contentState로 만들기 */
    axios.get('http://localhost/trip/content')
        .then((response) => {
            contentList.value = response.data
        })
})

const getGugun = () => {
    axios.get('http://localhost/trip/gugun', {
        params: {
            sido_code: sido.value
        }
    }).then((response) => {
        gugunList.value = response.data
    })
}

const search = (async () => {
    if (sido.value == "0") {
        msg.value.textContent = "시도를 선택해주세요"
    } else {
        const data = await searchAttraction(sido, gugun, type, keyword);
        if (data.length == 0) {
            alert("검색 결과가 없습니다.");
        } else {
            areas.value = data;
            positions = []
            areas.value.forEach((area) => {
                let position = {
                    contentId: area.contentId,
                    title: area.title,
                    latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
                    contentTypeId: area.contentTypeId,
                    contentType: area.contentType,
                    addr: area.addr1 + " " + area.addr2,
                    imgUrl: area.imgUrl,

                };
                positions.push(position)
            })
            mapComponent.value.displayMarkerByContent(positions, markers)
        }
    }

})

const addPlaceToList = (place) => {
    selectedPlaces.value.push(place)
    emit('update-places', selectedPlaces.value);
}

const deletePlaceToList = (index) => {
    selectedPlaces.value.splice(index, 1);
}

const createCourse = () => {
    const createCourseElement = document.getElementById('create-course');
    if (createCourseElement) {
        createCourseElement.scrollIntoView({ behavior: 'smooth' });
    }
};
</script>

<template>
    <div class="col-md-12 justify-content-center mt-5 position-relative">
        <div class="row">
            <div class="col-md-1">
            </div>
            <div class="col-md-2">
                <select id="sido" class="form-select me-2" name="sido" @change="getGugun" v-model="sido">
                    <option value="0" selected>시도선택</option>
                    <option v-for="sido in sidoState.sidoList" :key="sido.sido_code" :value="sido.sido_code">{{
                        sido.sido_name }}
                    </option>
                </select>
                <div id="msg" class="mt-1" style="color:red" ref="msg"></div>
            </div>
            <div class="col-md-2">
                <select id="gugun" class="form-select me-2" name="gugun" v-model="gugun">
                    <option v-if="sido == 0" value="0" selected>구군선택</option>
                    <option value="0" selected>전체</option>
                    <option v-for="gugun in gugunList" :key="gugun.code" :value="gugun.code">{{
                        gugun.name
                        }} </option>
                </select>
            </div>
            <div class="col-md-2">
                <select id="search-content-id" class="form-select me-2" name="type" v-model="type">
                    <option value="0">전체</option>
                    <option v-for="content in contentList" :key="content.contentTypeId" :value="content.contentTypeId">
                        {{ content.contentType }}
                    </option>
                </select>
            </div>

            <div class="col-md-3">
                <input type="text" class="form-control" v-model="keyword">
            </div>
            <div class="col-md-2">
                <button id="btn-search" class="btn btn-outline-success" type="button" @click="search">검색</button>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-md-12">
                <KakaoMap ref="mapComponent" @selected-place="addPlaceToList" />
            </div>
            <div class="col-md-3 plan-overlay">
                <div class="mb-2 text-center">방문하고 싶은 장소를 추가해보세요</div>
                <table>
                    <tr v-for="(place, index) in selectedPlaces" :key="index">
                        <td class="indexes">
                            <span class="index">{{ index + 1 }}</span>
                        </td>
                        <td class="title">
                            {{ place.title }}
                        </td>
                        <td class="options">
                            <img src="https://velog.velcdn.com/images/gangintheremark/post/e6370c0c-4f44-4a66-b7e3-a7f6e8887d7b/image.png"
                                width=20 class="pointer" @click="deletePlaceToList(index)">
                        </td>
                    </tr>
                </table>
                <div class="text-center"> <button class="btn" @click="createCourse" v-if="selectedPlaces.length">코스
                        생성</button></div>
            </div>
        </div>
    </div>
</template>

<style scoped>
#map {
    width: 100%;
    height: 600px;
}

.plan-overlay {
    position: absolute;
    top: 80px;
    left: 20px;
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 5px;
    padding: 10px;
    z-index: 10;
    max-height: 600px;
    overflow-y: auto;
}

.index {
    background-color: rgb(130, 203, 196);
    color: white;
    padding: 3px 8px;
    border-radius: 30px;
}

.indexes {
    width: 14%;
}

.title {
    width: 80%;
}

.options {
    width: 22%;
    text-align: center;
}
</style>