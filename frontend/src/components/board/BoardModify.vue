<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <div class="mt-3 text-center fw-bold" style="font-size: 2rem;">
            </div>
            <div class="form-group">
                <input type="text" name="title" class="form-control" placeholder="제목을 입력하세요" id="title"
                    v-model="board.title">
            </div>

            <div class="form-group mt-3">
                <textarea class="form-control content" rows="10" id="content" name="content"
                    style="white-space: pre-wrap" v-model="board.content"></textarea>
            </div>
            <div class="text-center mt-2">
                <button id="btn-save" class="btn me-2" @click="write">수정</button>
                <button id="cancel-btn" class="btn" @click="cancel">취소</button>
            </div>


        </div>
    </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import router from "@/router";

const route = useRoute();
const boardNo = route.params.boardNo;
const board = ref({});

onMounted(() => {
    axios.get(`http://localhost/board/${boardNo}`)
        .then((response) => {
            board.value = response.data;
        })
})

const cancel = () => {
    window.history.back();
}

const write = () => {
    axios.put(`http://localhost/board/${boardNo}`, {
        title: board.value.title,
        content: board.value.content
    }).then(response => {
        router.push({
            name: 'BoardDetail',
            params: {
                boardNo: boardNo
            }
        })
    }).catch(error => {
        console.log("fail modify board")
        throw error
    })
}

</script>

<style scoped>
.input-form {
    max-width: 1000px;
    margin-top: 40px;
    padding: 32px;
    background: #fff;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    border-radius: 10px;
    -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15)
}

#title {
    font-size: 2rem;
    border: none;
}

#title:focus,
#content:focus {
    box-shadow: none;
    border-color: rgb(223, 223, 223);
}
</style>