<template>
    <header
        class="d-flex flex-wrap align-items-center justify-content-center justify-content-md-between border-bottom mt-2">
        <RouterLink to="/" class="logo"> <img
                src="https://velog.velcdn.com/images/gangintheremark/post/a9f26507-cc25-4cb0-9571-61f3aaabdb67/image.png"
                alt="로고" class="logo"> </RouterLink>
        <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0 nav-links">
            <li>
                <RouterLink to="/trip" class="nav-link">여행지도</RouterLink>
            </li>
            <li>
                <RouterLink to="/review" class="nav-link mx-4">여행리뷰</RouterLink>
            </li>
            <li>
                <RouterLink to="/plan" class="nav-link me-4">여행코스</RouterLink>
            </li>
            <li>
                <RouterLink to="/board" class="nav-link">Q&A게시판</RouterLink>
            </li>
        </ul>
        <div class="col-md-3 text-end user-link">
            <ul class="nav col-12 col-md-auto mb-3 justify-content-center mb-md-0">
                <div v-if="userInfo.userId">
                    <li class="nav-item" id="profile">
                        <div class="btn-group">
                            <a class="nav-link small-text btn dropdown-toggle" aria-current="page" href="#"
                                data-bs-toggle="dropdown" aria-expanded="false"> <img class="profile mx-2"
                                    :src="userInfo.profileImg" width="35" height=auto> <span id="header-id"><b>{{
                                        userInfo.userName
                                        }}</b>
                                </span> <span style="color: black;"> 님 </span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <button class="dropdown-item" @click="logout">로그아웃</button>
                                </li>
                                <li>
                                    <RouterLink to="/mypage" class="dropdown-item">회원정보</RouterLink>
                                </li>
                            </ul>
                        </div>
                    </li>

                </div>
                <div v-else>
                    <ul class="nav col-12 col-md-auto mb-2 justify-content-center mb-md-0">
                        <li class="form-group">
                            <RouterLink to="/login" class="nav-link">로그인</RouterLink>
                        </li>
                        <li class="form-group">
                            <RouterLink to="/join" class="nav-link">회원가입</RouterLink>
                        </li>
                    </ul>
                </div>
            </ul>
        </div>
    </header>

</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const userInfo = ref({});
const router = useRouter();

onMounted(() => {
    const storedUserInfo = sessionStorage.getItem('userInfo')
    if (storedUserInfo)
        userInfo.value = JSON.parse(storedUserInfo)
})

const logout = () => {
    sessionStorage.removeItem('userInfo')
    router.push({ name: 'Home' })
        .then(() => {
            window.location.reload();
        });
}
</script>

<style scoped>
.logo {
    width: 250px;
    height: auto;
}

.nav-links {
    font-size: 19px;
}

.nav-link {
    padding: 0px 10px;
    text-decoration: none;
    color: #292929;
}

.nav-link:hover {
    color: rgb(130, 203, 196);
}

.nav-link:focus {
    color: rgb(130, 203, 196);
}

.user-link {
    font-size: 15px;
    padding-bottom: 60px;
    margin-bottom: 30px;
}
</style>