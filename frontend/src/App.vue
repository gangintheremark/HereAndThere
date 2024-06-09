<script setup>
import Header from "@/components/common/Header.vue";
import Footer from "@/components/common/Footer.vue";
import { provide } from "vue";
import { sidoState, getSido } from '@/api/sido.js'
import { userState, getUserInfo } from "@/api/user.js"

provide('sidoState', sidoState);
provide('userState', userState);

getSido();
getUserInfo();
</script>

<template>
  <div class="container">
    <Header />
    <RouterView />

  </div>

</template>

<style>
@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: 400;
  font-style: normal;
}

* {
  font-family: 'Pretendard-Regular';
}

a {
  text-decoration: none !important;
}

ul {
  padding-left: 0rem !important;
}

li {
  list-style: none;
}

.btn:not(.dropdown-toggle) {
  color: white !important;
  background-color: rgb(130, 203, 196) !important;
  border-color: rgb(130, 203, 196) !important;
}

.btn:hover {
  color: black !important;
}

#cancel-btn {
  background-color: gray !important;
  border-color: gray !important;
}

#login {
  color: rgb(130, 203, 196);
}

.link {
  color: white;
}

.pointer {
  cursor: pointer;
}

.gray {
  color: gray;
  font-size: 0.9rem;
}

.scroll-container {
  overflow-y: auto;
  max-height: 600px;
  padding-right: 10px;
}

.scroll-container::-webkit-scrollbar {
  width: 12px;
}

.scroll-container::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.scroll-container::-webkit-scrollbar-thumb {
  background: rgb(130, 203, 196);
  border-radius: 10px;
}

.scroll-container::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.area-tag {
  font-size: 0.9rem;
  padding: 5px 10px;
  background-color: rgba(237, 238, 239, 0.884);
  color: rgb(44, 183, 169);
  border-radius: 20px;
}

.profile {
  border-radius: 50%;
}
</style>