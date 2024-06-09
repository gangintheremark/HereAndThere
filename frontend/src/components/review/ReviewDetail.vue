<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <div>
                <span class="area-tag me-3">{{ getCodeToTag(review.sidoCode) }}</span>
                <span v-for="i in review.starRating" :key="i">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/2844beb0-2801-4e96-994a-863b7616944d/image.png"
                        width="22">
                </span>
            </div>
            <div>
                <span class="fw-bold me-2" style="font-size: 2rem;">{{ review.title }}</span>
            </div>
            <div style="font-size: 0.9rem; color:gray">
                <span id="userid">{{ review.userId }}</span>
                <span id="date" class="mx-2">{{ review.createAt }}</span>
                <a href="#" class="mx-1" v-if="userInfo.userId == review.userId" @click="remove">삭제</a>
            </div>
            <hr>
            <div class="form-group mt-4 mb-5">
                <img :src="review.imgUrl" width="50%">
                <p id="content" class="mt-3">{{ review.content }}</p>
            </div>
            <div class="mt-2 pb-3 row">
                <div class="text-center">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/cc46c572-c58b-49db-a64c-8cf5c5589e8b/image.png"
                        class="like-btn" v-if="isLiked" @click="cancel">
                    <img src="https://velog.velcdn.com/images/gangintheremark/post/bf7a2ac1-4f48-4e7a-9dcd-045ce12da7c0/image.png"
                        class="like-btn" v-else @click="like">
                    <p>{{ review.likes }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue'
import { useRoute } from "vue-router";
import { getReviewDetail, cancleLike, LikeReview, removeReview } from '@/api/review.js'
import { getCodeToTag } from '@/api/sido';

const route = useRoute();
const reviewId = route.params.reviewId;
const userState = inject('userState');
const userInfo = userState.userInfo;
const review = ref({});
const isLiked = ref(false);

onMounted(() => {
    setReviewDetail(reviewId, userInfo.userId);
})

const setReviewDetail = (reviewId, userId) => {
    getReviewDetail(reviewId, userId)
        .then((response) => {
            review.value = response.review
            isLiked.value = response.liked
        }).catch(error => {
            console.error("fetch error", error);
        })
}

const like = () => {
    if (review.value.userId == userInfo.userId) {
        alert("본인 게시물은 추천하실 수 없습니다.");
    } else if (!userInfo.userId) {
        alert("추천하시려면 로그인이 필요합니다");
    } else {
        isLiked.value = true;
        LikeReview(reviewId, userInfo.userId)
        setReviewDetail(reviewId, userInfo.userId);
        window.location.reload();
    }
}

const cancel = () => {
    isLiked.value = false;
    cancleLike(reviewId, userInfo.userId)
    setReviewDetail(reviewId, userInfo.userId);
    window.location.reload();
}

const remove = () => {
    removeReview(reviewId)
}

</script>

<style scoped>
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