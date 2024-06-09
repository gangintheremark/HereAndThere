<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <div>
                <span class="area-tag me-3">{{ getCodeToTag(plan.sidoCode) }}</span>
            </div>
            <div>
                <span class="fw-bold me-2" style="font-size: 2rem;">{{ plan.title }}</span>
            </div>
            <div style="font-size: 0.9rem; color:gray">
                <span id="userid">{{ plan.userId }}</span>
                <span id="date" class="mx-2">{{ plan.createAt }}</span>
                <a href="#" class="mx-1" v-if="userInfo.userId == plan.userId" @click="remove">삭제</a>
            </div>
            <hr>
            <div class="form-group mt-4 mb-5">
                <KakaoMap ref="mapComponent" />
            </div>
            <div class="text-center">
                <div class="mt-4" style="font-size: 1.2rem;">🚗 나의 여행코스 🚗</div>
                <ul class=" mt-3">
                    <li v-for="(position, index) in positions" :key="position.contentId">
                        <div><span>{{ index + 1 }}. </span> {{ position.title }} </div>
                        <div class="gray">{{ position.addr }}</div>
                    </li>
                </ul>
                <div class="mt-3">총 코스거리 : {{ plan.distance }} km</div>
            </div>
            <div class="mt-2 pb-3 row">
                <div class="text-center">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                        class="like-btn" v-if="isLiked" @click="cancel">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/bf7a2ac1-4f48-4e7a-9dcd-045ce12da7c0/image.png"
                        class="like-btn" v-else @click="like">
                    <p>{{ plan.likes }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue'
import { useRoute } from "vue-router";
import { getCodeToTag } from '@/api/sido';
import KakaoMap from "../map/KakaoMap.vue";
import { getPlanDetail, LikePlan, cancleLike, removePlan } from "@/api/plan"

const route = useRoute();
const planId = route.params.planId;
const plan = ref({});
const isLiked = ref(false);
const userState = inject('userState');
const userInfo = userState.userInfo;
const mapComponent = ref('')

var positions = []
var markers = []
var polylines = []
var overlays = []

onMounted(() => {
    setPlanDetail(planId, userInfo.userId);
})

const setPlanDetail = (planId, userId) => {
    getPlanDetail(planId, userId)
        .then((response) => {
            plan.value = response.plan
            isLiked.value = response.liked

            positions = []
            response.places.forEach((place) => {
                let position = {
                    title: place.title,
                    latlng: new kakao.maps.LatLng(place.latitude, place.longitude),
                    contentTypeId: place.contentTypeId,
                    addr: place.addr1 + " " + place.addr2,
                    imgUrl: place.imgUrl,
                };
                positions.push(position)

            })
            mapComponent.value.displayMarkerWithPolyline(positions, markers, polylines, overlays);
        }).catch(error => {
            console.error("fetch error", error);
        })

}

const like = () => {
    if (plan.value.userId == userInfo.userId) {
        alert("본인 게시물은 추천하실 수 없습니다.");
    } else if (!userInfo.userId) {
        alert("추천하시려면 로그인이 필요합니다");
    } else {
        isLiked.value = true;
        LikePlan(planId, userInfo.userId)
        setPlanDetail(planId, userInfo.userId);
        window.location.reload();
    }
}

const cancel = () => {
    isLiked.value = false;
    cancleLike(planId, userInfo.userId)
    setPlanDetail(planId, userInfo.userId);
    window.location.reload();
}

const remove = () => {
    removePlan(planId)
}

</script>

<style scoped>
#map {
    width: 100%;
    height: 600px;
}

.input-form {
    text-align: center;
    max-width: 80% !important;
    margin-top: 20px;
    padding: 32px;
    border-radius: 20px;
    -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}

.like-btn {
    width: 33px;
    padding: 3px;
    cursor: pointer;
}
</style>