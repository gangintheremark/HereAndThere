<template>
    <div class="col-md-12 justify-content-center mt-5">
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
            <div class="col-md-8">
                <KakaoMap ref="mapComponent" />
            </div>
            <div class="col-md-4 scroll-container">
                <table class="table">
                    <tbody id="trip-list">
                        <tr v-for="area in areas" @click="moveCenter(area.latitude, area.longitude)" class="pointer">
                            <td><img :src="area.imgUrl" width="170px" class="rounded" v-if="area.imgUrl">
                                <span v-else class="gray" style="font-size: 0.9rem;">존재하는 이미지가 없습니다.</span>
                            </td>
                            <td><span style="font-size: 1.2rem;">{{ area.title }}</span> <br /> <span class="gray">{{
                                area.addr1 }} {{
                                        area.addr2 }}</span>
                            </td>

                        </tr>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, inject } from "vue";
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
                    title: area.title,
                    latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
                    contentTypeId: area.contentTypeId,
                    addr: area.addr1 + " " + area.addr2
                };
                positions.push(position)
            })
            mapComponent.value.displayMarkerByContent(positions, markers)
        }
    }

})

const moveCenter = (lat, lng) => {
    mapComponent.value.moveCenter(lat, lng)
}
</script>

<style scoped>
#map {
    width: 100%;
    height: 600px;
}
</style>