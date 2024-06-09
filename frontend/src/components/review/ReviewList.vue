<script setup>
import { ref, onMounted, inject } from 'vue'
import { getCodeToTag } from "@/api/sido.js"
import { getBestReviews, getPopularReviews, getRecentReviews } from "@/api/review.js"

const sidoState = inject('sidoState');
const userState = inject('userState');
const userInfo = userState.userInfo;
const selectedSido = ref(0)
const bestReviews = ref({})
const reviewList = ref({})
const activeButton = ref('recent');

onMounted(() => {
    setBestReviews();
    setRecentReviews();
});

const setBestReviews = () => {
    getBestReviews()
        .then((reviews) => {
            bestReviews.value = reviews;
        }).catch(error => {
            console.error("fetch error", error);
        })
}

const setRecentReviews = () => {
    activeButton.value = 'recent';
    getRecentReviews(selectedSido.value)
        .then((reviews) => {
            reviewList.value = reviews;
        }).catch(error => {
            console.error("fetch error", error);
        })
};

const setPopularReviews = () => {
    activeButton.value = 'popular';
    getPopularReviews(selectedSido.value)
        .then((reviews) => {
            reviewList.value = reviews;
        }).catch(error => {
            console.error("fetch error", error);
        })
};

const selectSido = (sidoCode) => {
    selectedSido.value = sidoCode;
    setRecentReviews();
};

const isActive = (sidoCode) => {
    return selectedSido.value === sidoCode;
};

const trimTitle = (title, len) => {
    return title.length > len ? title.substring(0, len) + '...' : title;
};

const trimContent = (content) => {
    return content.length > 35 ? content.substring(0, 35) + ' ...' : content;
};

</script>
<template>
    <div class="row mt-5">
        <div class="col-md-9">
            <div class="category">
                <div class="me-3" style="display: inline-block;">
                    <RouterLink to="/review/write" class="link" v-if="userInfo.userId">
                        <img src="https://velog.velcdn.com/images/gangintheremark/post/210ea2dd-8771-4fb8-b59b-11def4101a17/image.png"
                            width="26">
                    </RouterLink>
                    <div class="me-3" v-else>리뷰를 작성하려면 <RouterLink to="/login" id="login">로그인</RouterLink>이 필요합니다.</div>
                </div>
                <button class="category-btn" :class="{ active: activeButton === 'recent' }"
                    @click="setRecentReviews">최신순</button>
                <span class="mx-1">|</span>
                <button class="category-btn" :class="{ active: activeButton === 'popular' }"
                    @click="setPopularReviews">인기순</button>
            </div>
            <hr>
            <ul>
                <li v-for="review in reviewList" :key="review.reviewId">
                    <RouterLink :to="`/review/${review.reviewId}`">
                        <div class="row">
                            <div class="col-md-11">
                                <img :src="review.imgUrl" class="thumbnail me-4">
                                <span class="area-tag me-3">{{ getCodeToTag(review.sidoCode) }}</span>
                                <div class="title mt-2 mb-1">{{ trimTitle(review.title, 25) }}
                                </div>
                                <div class="gray mb-1">{{ trimContent(review.content) }}</div>
                                <span v-for="i in review.starRating" :key="i"> <img
                                        src="https://velog.velcdn.com/images/gangintheremark/post/2844beb0-2801-4e96-994a-863b7616944d/image.png"
                                        width="22"></span>
                            </div>
                            <div class="col-md-1">
                                <div class="like-badge">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                                        width="18" class="me-1">
                                    <p class="ms-1"> {{ review.likes }}</p>
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
                <div v-for="review in bestReviews" :key="review.reviewId" class="mt-3">
                    <RouterLink :to="`/review/${review.reviewId}`">
                        <div class="row">
                            <div class="col-md-4 position-relative">
                                <img :src="review.imgUrl" class="thumbnail-small me-3">
                                <div class="like-badge-small">
                                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                                        width="18">
                                    {{ review.likes }}
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="title-small">{{ trimTitle(review.title, 15) }}</div>
                                <div class="gray">{{ review.userId }}</div>
                                <div class="mt-1">
                                    <span v-for="i in review.starRating" :key="i">
                                        <img src="https://velog.velcdn.com/images/gangintheremark/post/2844beb0-2801-4e96-994a-863b7616944d/image.png"
                                            width="18">
                                    </span>
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
</style>