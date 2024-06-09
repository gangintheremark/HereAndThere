<template>
	<div class="input-form-backgroud row">
		<div class="input-form col-md-12 mx-auto">
			<h3 style="text-align: center"><b>로그인</b></h3>
			<div class="form-floating mt-4">
				<input type="text" class="form-control" id="userid" name="userid" placeholder="id" v-model="userId"
					required /> <label for="userid">아이디</label>
			</div>

			<div class="form-floating mt-3">
				<input type="password" class="form-control" id="passwd" name="passwd" v-model="userPwd" required
					placeholder="password" />
				<label for="floatingPassword">비밀번호</label>
			</div>
			<div id="msg" class="mt-3 ms-2" style="color: red;">

			</div>
			<div class="mt-4 text-center">
				<button id="btn-login" class="btn btn-primary" @click="login"> 로그인</button>
				<div class="d-flex justify-content-center mt-4">
					<RouterLink to="/join" class="nav-link">회원가입</RouterLink>
					<span style="color: gray"> &nbsp; | &nbsp;</span>
					<RouterLink to="/login" class="nav-link">비밀번호 찾기</RouterLink>
				</div>
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

const login = () => {

	if (!userId.value || !userPwd.value) {
		document.getElementById('msg').textContent = '아이디 또는 비밀번호를 입력해주세요.'

	} else {
		axios.post('http://localhost/user/login', {
			userId: userId.value,
			userPwd: userPwd.value
		})
			.then(response => {
				const userInfo = response.data;
				console.log(userInfo)
				if (!userInfo) {
					document.getElementById('msg').textContent = '아이디 또는 비밀번호를 잘못 입력했습니다.'
				} else {
					sessionStorage.setItem('userInfo', JSON.stringify(userInfo))
					router.push({ name: 'Home' })
						.then(() => {
							window.location.reload();
						});
				}
			})
			.catch(error => {
				console.log("로그인 실패")
			})
	}
}
</script>

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
</style>