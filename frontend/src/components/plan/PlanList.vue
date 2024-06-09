<script setup>
import { ref, onMounted, inject } from 'vue'
import { getCodeToTag } from "@/api/sido.js"
import { getBestPlans, getPopularPlans, getRecentPlans } from "@/api/plan";

const sidoState = inject('sidoState');
const userState = inject('userState');
const userInfo = userState.userInfo;
const selectedSido = ref(0)
const bestPlans = ref({})
const planList = ref({})
const activeButton = ref('recent');

onMounted(() => {
    setBestPlans();
    setRecentPlans();

})

const setBestPlans = () => {
    getBestPlans().then((plans) => {
        bestPlans.value = plans;
    }).catch(error => {
        console.error("fetch error", error);
    })
}

const setRecentPlans = () => {
    activeButton.value = 'recent';
    getRecentPlans(selectedSido.value)
        .then((plans) => {
            planList.value = plans;
        }).catch(error => {
            console.error("fetch error", error);
        })
    console.log(planList);
}

const setPopularPlans = () => {
    activeButton.value = 'popular';
    getPopularPlans(selectedSido.value)
        .then((plans) => {
            planList.value = plans;
        }).catch(error => {
            console.error("fetch error", error);
        })
}

const selectSido = (sidoCode) => {
    selectedSido.value = sidoCode;
    setRecentPlans();
};

const isActive = (sidoCode) => {
    return selectedSido.value === sidoCode;
};

const trimTitle = (title) => {
    return title.length > 25 ? title.substring(0, 25) + '...' : title;
};

const setImgUrl = (sidoCode) => {
    var imgUrl = ""
    switch (sidoCode) {
        case 1:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/13ce33ee-4cbd-4a06-a91e-b46d44ba3cf7/image.png"
            break;
        case 2:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/ad691b0f-1dbd-4963-9fef-c4b210bc5f6b/image.png"
            break;
        case 3:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/260d4581-65bb-46ae-8d82-3053216450f4/image.png"
            break;
        case 4:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/72cba757-56eb-4475-b0a3-08a409c3b739/image.png"
            break;
        case 5:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/b4bbbd34-a265-47ba-a0e0-1dcda998b3d2/image.png"
            break;
        case 6:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/0430df7c-e9d0-4207-bde9-6f4017206e07/image.png"
            break;
        case 7:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/2c87246e-feb0-4b74-a752-b18376839883/image.png"
            break;
        case 8:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/480d70b8-2efc-453e-9b33-bd1f9d382bba/image.png"
            break;
        case 31:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/3df322cc-5e8e-4f45-abc0-0cf7b9a2dde4/image.png"
            break;
        case 32:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/ee92e4a7-8ffa-4ef8-a26c-ccdfa19afe09/image.png"
            break;
        case 33:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/9c359e47-5cf6-4969-ae5d-8dde8a8afa7c/image.png"
            break;
        case 34:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/ba843720-679e-4f08-bb72-edc9efe291e7/image.png"
            break;
        case 35:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/17f52623-a662-482e-9113-9cf1f038d83e/image.png"
            break;
        case 36:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/9479564c-b3e6-4d19-974a-eccc09ed2b77/image.png"
            break;
        case 37:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/8f0a20bf-200f-4c85-81ee-c3d5d8ded845/image.png"
            break;
        case 38:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/01e6585c-91b6-43e6-84cd-f4cbf8ee5e49/image.png"
            break;
        case 39:
            imgUrl = "https://velog.velcdn.com/images/gangintheremark/post/3f55a7dd-9fa4-4ce5-bab3-7d8cb2021425/image.png"
            break;
    }
    return imgUrl
}

</script>
<template>
    <div class="row mt-5">
        <div class="col-md-9">
            <div class="category">
                <div class="me-3" style="display: inline-block;">
                    <RouterLink to="/plan/write" class="link" v-if="userInfo.userId">
                        <img src="https://velog.velcdn.com/images/gangintheremark/post/210ea2dd-8771-4fb8-b59b-11def4101a17/image.png"
                            width="26">
                    </RouterLink>
                    <div class="me-3" v-else>여행코스를 생성하려면 <RouterLink to="/login" id="login">로그인</RouterLink>이 필요합니다.
                    </div>
                </div>
                <button class="category-btn" :class="{ active: activeButton === 'recent' }"
                    @click="setRecentPlans">최신순</button>
                <span class="mx-1">|</span>
                <button class="category-btn" :class="{ active: activeButton === 'popular' }"
                    @click="setPopularPlans">인기순</button>
            </div>
            <hr>
            <ul>
                <li v-for="plan in planList" :key="plan.planId">
                    <RouterLink :to="`/plan/${plan.planId}`">
                        <div class="row">
                            <div class="col-md-11">
                                <img :src="setImgUrl(plan.sidoCode)" class="thumbnail me-4">
                                <span class="area-tag">{{ getCodeToTag(plan.sidoCode) }}</span>
                                <div class="title mt-2 mb-1" style="font-size: 1.3rem;">{{ plan.title }}</div>
                                <div class="distance">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/557f4485-639e-47cc-9d86-06e1585011d1/image.png"
                                        width="20"> 코스 총 거리: {{ plan.distance }} km
                                </div>
                            </div>
                            <div class="col-md-1">
                                <div class="like-badge">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                                        width="18" class="me-1">
                                    <p class="ms-1">{{ plan.likes }}</p>
                                </div>
                            </div>
                        </div>
                    </RouterLink>
                </li>
            </ul>
        </div>
        <div class="col-md-3">
            <div class="tag-list">
                <a class="tag me-3" :class="{ 'active': isActive(0) }" @click="selectSido(0)">#전체</a>
                <a class="tag me-3" v-for="sido in sidoState.sidoList" :key="sido.sido_code"
                    :class="{ active: isActive(sido.sido_code) }" @click="selectSido(sido.sido_code)">#{{
                        sido.sido_name }}</a>
            </div>
            <div class="mt-4">
                <div class="text-center">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                        width="18" class="pt-1 pb-1 mx-2">
                    <span class="fw-bold">BEST 3</span>
                </div>
                <div v-for="plan in bestPlans" :key="plan.planId" class="mt-3">
                    <RouterLink :to="`/plan/${plan.planId}`">
                        <div class="row">
                            <div class="col-md-4 position-relative">
                                <img :src="setImgUrl(plan.sidoCode)" class="thumbnail-small me-3">
                                <div class="like-badge-small">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                                        width="18">
                                    {{ plan.likes }}
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="title-small">{{ trimTitle(plan.title, 15) }}</div>
                                <div class="gray">{{ plan.userId }}</div>
                                <div class="distance" style="font-size: 0.9rem;">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/557f4485-639e-47cc-9d86-06e1585011d1/image.png"
                                        width="20"> 코스 총 거리: {{ plan.distance }} km
                                </div>
                            </div>
                        </div>
                    </RouterLink>
                </div>


            </div>
        </div>
    </div>

</template>


<style scoped>
li {
    border-bottom: 1px solid rgb(243, 243, 243);
    padding: 20px 10px;
}

.position-relative {
    position: relative;
}

.title {
    font-size: 1.2rem;
    color: black;
}

.title:hover {
    color: rgb(130, 203, 196);
}

.title-small {
    color: black;
    font-size: 1rem;
}

.tag-list {
    padding: 20px 20px;
    display: flex;
    flex-wrap: wrap;
    background-color: rgb(245, 245, 245);
    border: 1px solid rgb(245, 245, 245);
    border-radius: 40px;
    min-height: 20px;
    justify-content: center;
    width: 300px;
}

.tag {
    color: gray;
    font-size: 1.1rem;
    cursor: pointer;
    margin-bottom: 10px;
}

.category {
    text-align: right;
}

.category-btn {
    border: none;
    background-color: white;
    color: gray;
}

.tag.active,
.category-btn.active {
    color: black;
}

.thumbnail {
    width: 180px;
    height: 150px;
    overflow: hidden;
    display: flex;
    float: left;
    object-fit: cover;
    object-position: center;
}


.thumbnail-small {
    width: 100px;
    height: 70px;
    overflow: hidden;
    display: flex;
    float: left;
    object-fit: cover;
    object-position: center;
}

.thumbnail,
.thumbnail-small {
    border-radius: 10px;
}


.like-badge,
.like-badge-small {
    color: black;
}

.like-badge-small {
    position: absolute;
    top: 0;
    left: 0;
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 20px;
    padding: 2px 5px;
    font-size: 14px;
    font-weight: bold;
}

.distance {
    color: rgb(104, 104, 104);
}
</style>