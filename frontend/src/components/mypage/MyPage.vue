<script setup>
import { ref, onMounted, inject } from 'vue'
import { modifyMyPage } from '@/api/user'
import router from "@/router";

const userState = inject('userState');
const userInfo = userState.userInfo
const profileImg = ref(userState.userInfo.profileImg)
const userId = ref(userState.userInfo.userId)
const userPwd = ref(userState.userInfo.userPwd)
const userName = ref(userState.userInfo.userName)
const email = ref(userState.userInfo.email)
const fileInput = ref(null)
const fileName = ref('');

const getFileName = () => {
    if (fileInput.value && fileInput.value.files.length > 0)
        fileName.value = fileInput.value.files[0].name;
}

const modify = async () => {
    try {
        const uploadFile = fileInput.value.files[0];
        const data = modifyMyPage(userId.value, userName.value, userPwd.value, email.value, uploadFile)
        alert("회원정보가 수정되었습니다.");
        router.push({ name: 'MyPage' })
            .then(() => {
                window.location.reload();
            });
    } catch (error) {
        console.error("Error creating review:", error);
    }
}

</script>

<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <div class="profile text-center">
                <img :src="userState.userInfo.profileImg" class="profile-img">
            </div>
            <div class="mt-2">
                <input type="file" id="upload-image" hidden @change="getFileName" ref="fileInput" />
                <label for="upload-image">
                    <div class="px-3 py-1 file"> <img
                            src="https://velog.velcdn.com/images/gangintheremark/post/2c2dd4c1-3393-44f5-ac8e-7a56ca9b67c1/image.png"
                            width="25" /> &nbsp; 프로필 업로드 </div>
                </label> <span class="mx-2">{{ fileName }}</span>

            </div>

            <div class="form-floating mt-3">
                <input type="text" class="form-control" id="userid" name="userid" placeholder="id" required
                    v-model="userId" disabled /> <label for="userid">아이디</label>
            </div>

            <div class="form-floating mt-3">
                <input type="password" class="form-control" id="passwd" name="passwd" v-model="userPwd" disabled
                    placeholder="password" />
                <label for="floatingPassword">비밀번호</label>
            </div>

            <div class="form-floating mt-3">
                <input type="text" class="form-control" id="nickname" name="nickname" v-model="userName"
                    placeholder="nickname" /> <label for="nickname">닉네임</label>
            </div>

            <div class="form-floating mt-3">
                <input type="email" class="form-control" id="email" name="email" v-model="email" placeholder="email" />
                <label for="floatingPassword">이메일</label>
            </div>
            <div class="mt-4 text-center">
                <button id="btn-login" class="btn btn-primary" @click="modify"> 수정 </button>
            </div>
        </div>
    </div>

</template>

<style scoped>
.input-form {
    max-width: 400px !important;
    margin-top: 40px;
    padding: 32px;
    background: #fff;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    border-radius: 10px;
    -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
    box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}

.profile-img {
    width: 200px;
    border-radius: 50%;
}
</style>