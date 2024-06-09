<template>
    <div class="input-form-backgroud row mt-5">
        <div class="input-form col-md-12 mx-auto">
            <div class="mt-3 text-center fw-bold" style="font-size: 2rem;">
            </div>
            <form id="form-write" method="POST">
                <div class="form-group">
                    <input type="text" name="title" class="form-control" placeholder="제목을 입력하세요" id="title"
                        v-model="title">
                </div>
                <div class="area ms-2 my-2">
                    <button type="button" id="tag-btn" data-bs-toggle="modal" data-bs-target="#areaModal">
                        # 지역을 태그해주세요
                    </button>
                    <div class="area-tag ms-3" style="display: inline-block;" v-if="selectedSido.length">
                        {{ selectedSido }}
                    </div>
                    <!-- Modal -->
                    <div class="modal fade" id="areaModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="tags-container">
                                        <a href="" class="area-tag me-2 mt-2" data-bs-dismiss="modal"
                                            v-for="sido in sidoState.sidoList" :key="sido.sido_code"
                                            @click="getSidoCode(sido.sido_code, sido.sido_name)">{{
                                                sido.sido_name }}</a>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn" id="cancel-btn"
                                        data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Modal END -->
                </div>
                <div name="rating" id="rating">
                    <fieldset style="text-align: center">
                        <input type="radio" name="rating" value="5" id="rate5" v-model="starRating"><label
                            for="rate5">⭐</label>
                        <input type="radio" name="rating" value="4" id="rate4" v-model="starRating"><label for="rate4"
                            class="mx-1">⭐</label>
                        <input type="radio" name="rating" value="3" id="rate3" v-model="starRating"><label
                            for="rate3">⭐</label>
                        <input type="radio" name="rating" value="2" id="rate2" v-model="starRating"><label for="rate2"
                            class="mx-1">⭐</label>
                        <input type="radio" name="rating" value="1" id="rate1" v-model="starRating"><label
                            for="rate1">⭐</label>
                    </fieldset>
                </div>


                <div class="form-group mt-3">
                    <textarea class="form-control content" rows="10" id="content" name="content" placeholder="내용을 입력하세요"
                        v-model="content"></textarea>
                </div>
                <div class="mt-2"> <input type="file" id="upload-image" hidden @change="getFileName" ref="fileInput" />
                    <label for="upload-image">
                        <div class="px-3 py-1 file"> <img
                                src="https://velog.velcdn.com/images/gangintheremark/post/2c2dd4c1-3393-44f5-ac8e-7a56ca9b67c1/image.png"
                                width="25" /> &nbsp; 파일 업로드 </div>
                    </label> <span class="mx-2">{{ fileName }}</span>
                </div>
                <div class="text-center">
                    <button type="submit" id="btn-save" class="btn btn-primary mt-3" @click="writeReview">글쓰기</button>
                </div>
            </form>

        </div>
    </div>
</template>

<script setup>
import { ref, inject } from 'vue'
import { createReview } from '@/api/review.js'
import { useRouter } from 'vue-router'

const router = useRouter();
const userState = inject('userState');
const userInfo = userState.userInfo;

const sidoState = inject('sidoState');
const fileName = ref('');
const fileInput = ref(null)

var selectedSido = ref('')
var selectedSidoCode = ref('')
const title = ref('');
const content = ref('');
const starRating = ref('');

const getSidoCode = (sidoCode, sidoName) => {
    selectedSidoCode.value = sidoCode
    selectedSido.value = sidoName
}

const getFileName = () => {
    if (fileInput.value && fileInput.value.files.length > 0)
        fileName.value = fileInput.value.files[0].name;
}

const writeReview = async () => {
    try {
        const uploadFile = fileInput.value.files[0];
        const status = createReview(userInfo.userId, title.value, content.value, selectedSidoCode.value, starRating.value, uploadFile)
        alert("리뷰가 등록되었습니다.");
        router.push('/review');
    } catch (error) {
        console.error("Error creating review:", error);
    }
}

</script>

<style scoped>
.input-form {
    padding: 0 100px;
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

#rating fieldset {
    display: inline-block;
    border: 1px;
    direction: rtl;
    margin-left: 7px;
}

#rating input[type=radio] {
    display: none;
}

#rating input[type=radio]:checked~label {
    color: activeborder;
}

#rating label:hover,
#rating label:hover~label {
    color: activeborder;
}

#rating label {
    font-size: 1.5rem;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
}

.file {
    cursor: pointer;
    text-align: center;
    background-color: rgb(243, 243, 243);
    border: 1px solid rgb(206, 206, 206);
    border-radius: 30px;
}

#tag-btn {
    padding: 5px 10px;
    border: 1px solid rgb(192, 192, 192);
    border-radius: 20px;
    background-color: white;
    color: gray;
}

#tag-btn:hover {
    color: black;
    border: 1px solid rgb(173, 173, 173);
    background-color: rgb(245, 245, 245);
}

.modal .area-tag:hover {
    font-size: 1.1rem;
}

.tags-container {
    display: flex;
    flex-wrap: wrap;
}
</style>