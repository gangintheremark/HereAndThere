<script setup>
import { ref, inject } from 'vue';
import axios from 'axios';
import router from "@/router";

const userState = inject('userState');
const userInfo = userState.userInfo;
const title = ref("");
const content = ref("");

const createBoard = () => {
	const body = {
		userId: userInfo.userId,
		title: title.value,
		content: content.value
	};

	axios.post('http://localhost/board/',
		body
	).then(response => {
		router.push({ name: 'BoardList' });
	}).catch(error => {
		console.log(error);

	});
}

const resetValues = () => {
	title.value = "";
	content.value = "";
}

</script>

<template>
	<div class="input-form-backgroud row">
		<div class="input-form col-md-12 mx-auto">
			<div class="mt-3 text-center fw-bold" style="font-size: 2rem;">
			</div>

			<form v-on:submit.prevent="createBoard" id="form-write" method="POST">
				<div class="form-group">
					<input v-model="title" type="text" name="title" class="form-control" placeholder="제목을 입력하세요"
						id="title">
				</div>

				<div class="form-group mt-3">
					<textarea v-model="content" class="form-control content" rows="10" id="content"
						placeholder="내용을 입력하세요" name="content"></textarea>
				</div>
				<div class="text-center mt-3">
					<button type="submit" id="btn-save" class="btn btn-primary">글쓰기</button>
					<button type="reset" id="cancel-btn" class="btn mx-2" @click="resetValues">초기화</button>
				</div>
			</form>

		</div>
	</div>
</template>

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