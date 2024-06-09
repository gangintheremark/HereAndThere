<script setup>
import { onMounted, ref, inject } from "vue";
import router from "@/router";
import axios from 'axios';


const userState = inject('userState');
const userInfo = userState.userInfo;

const pageNo = ref(1);
const keyword = ref('');
const searchKeyword = ref('');
const pageCnt = 5;
const allPageCnt = ref();
const boardList = ref([]);

onMounted(() => {
	getBoardListInfo(pageNo, keyword.value);
});


const getBoardListInfo = () => {
	axios.get(`http://localhost/board/list/search`, {
		params: {
			keyword: keyword.value,
			pageCnt: pageCnt,
			pageNum: pageNo.value
		}
	}).then((response) => {
		boardList.value = response.data.boardList;
		allPageCnt.value = Math.ceil(response.data.allPageCnt / pageCnt);
	});
}

//TODO : boardList 값만 바뀌어도 뷰 갱신되는 경우가 된다면 그 방법으로 시도
const movePage = (page) => {
	router.push({
		name: "BoardList",
		params: { pageNo: page },
		query: { keyword: keyword.value }
	}).then(() => {
		keyword.value = keyword.value;
		pageNo.value = page;
		getBoardListInfo();
	});
};

const searchByKeyword = () => {
	router.push({
		name: "BoardList",
		params: { pageNo: 1 },
		query: { keyword: searchKeyword.value }
	}).then(() => {
		keyword.value = searchKeyword.value;
		pageNo.value = 1;
		getBoardListInfo();
	});
}

</script>
<template>
	<div class="container-fluid px-4 mt-5">
		<div class="row mb-4">
			<form @submit.prevent="searchByKeyword" action="board" method="get" class="d-flex">
				<input v-model="searchKeyword" class="form-control me-2" type="search" placeholder="검색어 입력"
					aria-label="Search" name="keyword" style="width: 200px">
				<button id="search-btn" class="btn" type="submit">검색</button>
			</form>
		</div>


		<div class="card mb-3 text-center">
			<div class="card-header">
				<RouterLink to="/board/write" class="link mx-5">
					<button class="btn btn-primary float-end" v-if="userInfo.userId">글 작성</button>
				</RouterLink>
				<p v-if="!userInfo.userId">게시글을 작성하려면
					<RouterLink to="/login" id="login">로그인</RouterLink>이 필요합니다.
				</p>
			</div>
			<div class="card-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>글번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="board in boardList" :key="board.boardNo">
							<td>{{ board.boardNo }}</td>
							<td>
								<RouterLink :to="`/board/${board.boardNo}/detail`">
									{{ board.title }}
								</RouterLink>
							</td>
							<td>{{ board.userId }}</td>
							<td>{{ board.hits }}</td>
							<td>{{ board.createAt }}</td>
						</tr>
					</tbody>
				</table>
				<!-- Page -->
				<!-- TODO : css 수정 필요 -->
				<div class="pagination justify-content-center">
					<li class="page-item" v-for="index in allPageCnt" :key="index">
						<button @click="movePage(index)" class="page-link">
							{{ index }}
						</button>
					</li>
				</div>

			</div>
		</div>
	</div>
</template>

<style scoped>
a {
	color: black;
}

a:hover {
	color: rgb(130, 203, 196);
}
</style>