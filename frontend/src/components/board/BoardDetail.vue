<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <div class="fw-bold" style="font-size: 2rem;">
                <span id="title">{{ board.title }}</span>
            </div>
            <div style="font-size: 0.9rem; color:gray">
                <span id="userid">{{ board.userId }}</span>
                <span id="date" class="mx-2">{{ board.createAt }}</span>
                <RouterLink :to="`/board/${board.boardNo}/modify`" class="mx-1" v-if="userInfo.userId == board.userId">
                    수정
                </RouterLink>
                <a href="#" class="mx-1" @click="removeBoard" v-if="userInfo.userId == board.userId">삭제</a>
                <RouterLink :to="`/board`" class="mx-2">목록</RouterLink>
            </div>
            <div class="form-group mt-3 mb-5">
                <span id="content">{{ board.content }}</span>
            </div>
            <hr>
            <div class="mt-2 pb-3 row" v-if="userInfo.userId">
                <div class="col-md-1">
                    <img :src="userInfo.profileImg" width="35" class="profile">
                </div>
                <div class="col-md-9">
                    <input type="text" class="form-control" id="content" v-model="content">
                </div>
                <div class="col-md-2">
                    <button class="btn btn-primary mx-2" id="regist-btn" @click="createComment">등록</button>
                </div>
            </div>
            <div class="mt-2 pb-3 row" v-else>
                <div class="col-md-10">
                    <input type="text" class="form-control" id="content" value="댓글을 작성하려면 로그인이 필요합니다." disabled>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-primary mx-2" id="regist-btn" @click="createComment">등록</button>
                </div>
            </div>
            <div class="mt-3" v-for="comment in comments" :key="comment.commentNo">
                <img :src="comment.profileImg" width="35" class="me-3 profile">
                <b> {{ comment.userName }} </b> &nbsp;
                <span style="font-size: 0.8rem; color: gray;">
                    {{ comment.createAt }}</span>
                <a href="#" class="mx-2" style="font-size: 0.9rem;" @click="removeComment(comment.commentNo)"
                    v-if="userInfo.userId == comment.userId">삭제</a>
                <div class="ms-5">{{ comment.content }}</div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, inject } from "vue";
import { useRoute } from "vue-router";
import router from "@/router";
import axios from 'axios';

const userState = inject('userState');
const userInfo = userState.userInfo;

const route = useRoute();
const boardNo = route.params.boardNo;
const board = ref({});
const comments = ref([]);
const content = ref("");

onMounted(() => {
    axios.get(`http://localhost/board/${boardNo}`)
        .then((response) => {
            board.value = response.data;
        });

    axios.get(`http://localhost/comment/${boardNo}`)
        .then((response) => {
            console.log(response);
            comments.value = response.data;
        })


});

const removeBoard = () => {
    axios.delete(`http://localhost/board/${boardNo}`)
        .then(() => {
            router.push({ name: 'BoardList' });
        });
}

const removeComment = (commentNo) => {
    axios.delete(`http://localhost/comment/${commentNo}`)
        .then(() => {
            router.push({
                name: 'BoardDetail',
                params: {
                    boardNo: boardNo
                }
            }).then(() => {
                window.location.reload();
            })
        })
}

const createComment = () => {
    const body = {
        userId: userInfo.userId,
        boardNo: boardNo,
        content: content.value
    };

    axios.post('http://localhost/comment/',
        body
    ).then(response => {
        router.push({
            name: 'BoardDetail',
            params: {
                boardNo: boardNo
            }
        }).then(() => {
            window.location.reload();
        })
    }).catch(error => {
        console.log(error);

    });
}

</script>

<style scoped>
.input-form {
    max-width: 80% !important;
    margin-top: 20px;
    padding: 32px;
}

.col-md-1 {
    width: 6%;
}

.col-md-9 {
    width: 80%;
}

.col-md-2 {
    width: 14%;
}
</style>