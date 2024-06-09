<template>
    <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
            <h3 style="text-align: center">
                <b>회원가입</b>
            </h3>
            <div class="row">
                <div class="col-md-10">
                    <div class="form-floating mt-3">
                        <input type="text" class="form-control" id="userid" name="userid" v-model="userId"
                            placeholder="id" /> <label for="userid">아이디</label>

                    </div>
                </div>
                <div class="col-md-2 pt-4">
                    <button class="btn btn-primary">중복확인</button>
                </div>
            </div>

            <div class="row mt-3">
                <div class="form-floating col-md-6">
                    <input type="password" class="form-control" id="passwd" name="passwd" v-model="userPwd"
                        placeholder="password" /> <label for="floatingPassword">&nbsp;
                        비밀번호</label>
                </div>
                <div class="form-floating col-md-6">
                    <input type="password" class="form-control" id="passwd2" placeholder="password"
                        v-model="userPwdCheck" /> <label for="floatingPassword">&nbsp;
                        비밀번호 확인</label>

                </div>

            </div>


            <div class="form-floating mt-3">
                <input type="text" class="form-control" id="nickname" name="nickname" v-model="userName"
                    placeholder="nickname" /> <label for="nickname">닉네임</label>
            </div>

            <div class="form-floating mt-3">
                <input type="email" class="form-control" id="email" name="email" v-model="email" placeholder="email" />
                <label for="floatingPassword">이메일</label>
            </div>
            <div id="msg" style="color: red;" class="mt-2 ms-1"></div>

            <div class="mt-4" style="text-align: center">
                <button id="btn-join" class="btn btn-primary mx-2" type="button" @click="join">
                    회원가입</button>
                <input id="reset" class="btn btn-primary" type="reset" value="취소" />
            </div>

        </div>
    </div>

</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const userId = ref('')
const userPwd = ref('')
const userName = ref('')
const email = ref('')
const userPwdCheck = ref('')


const join = () => {
    console.log(userPwd.value + userPwdCheck.value)
    if (!userId.value || !userPwd.value || !userName.value || !email.value) {
        document.getElementById('msg').textContent = '모든 항목을 입력해주세요.'
    } else if (userPwd.value != userPwdCheck.value) {
        document.getElementById('msg').textContent = '비밀번호를 확인해주세요.'
    } else {
        axios.post('http://localhost/user/', {
            userId: userId.value,
            userPwd: userPwd.value,
            userName: userName.value,
            email: email.value
        }).then(() => {
            router.push({ name: 'Login' })
                .then(() => {
                    window.location.reload();
                });
        })
            .catch(error => {
                console.log("회원가입 실패")
            })
    }
}

const check = () => {
    if (userPwd.value != userPwdCheck.value)
        checkPwd = false;
    else
        checkPwd = true;
}
</script>

<style scoped>
.input-form {
    max-width: 680px !important;
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

#reset {
    background-color: gray !important;
    border-color: gray !important;
}
</style>