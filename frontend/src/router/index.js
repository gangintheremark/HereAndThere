import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import JoinView from "@/views/JoinView.vue";
import BoardView from "@/views/BoardView.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardDetail from "@/components/board/BoardDetail.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardModify from "@/components/board/BoardModify.vue";
import TripView from "@/views/TripView.vue";
import TripInfo from "@/components/trip/TripInfo.vue";
import TripReview from "@/views/ReviewView.vue";
import ReviewList from "@/components/review/ReviewList.vue";
import ReviewWrite from "@/components/review/ReviewWrite.vue";
import ReviewDetail from "@/components/review/ReviewDetail.vue";
import PlanView from "@/views/PlanView.vue";
import PlanList from "@/components/plan/PlanList.vue";
import PlanWrite from "@/components/plan/PlanWrite.vue";
import PlanDetail from "@/components/plan/PlanDetail.vue";
import MyPageView from "@/views/MyPageView.vue";
import MyPage from "@/components/mypage/MyPage.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: "/",
            name: "Home",
            component: HomeView,
        },
        {
            path: "/login",
            name: "Login",
            component: LoginView,
        },
        {
            path: "/join",
            name: "Join",
            component: JoinView,
        },
        {
            path: "/trip",
            component: TripView,
            children: [
                {
                    path: "",
                    name: "TripInfo",
                    component: TripInfo,
                },
            ],
        },
        {
            path: "/review",
            component: TripReview,
            children: [
                {
                    path: "",
                    name: "ReviewList",
                    component: ReviewList,
                },
                {
                    path: "write",
                    name: "ReviewWrite",
                    component: ReviewWrite,
                },
                {
                    path: ":reviewId",
                    name: "ReviewDetail",
                    component: ReviewDetail,
                },
            ],
        },
        {
            path: "/plan",
            component: PlanView,
            children: [
                {
                    path: "",
                    name: "PlanList",
                    component: PlanList,
                },
                {
                    path: "write",
                    name: "PlanWrite",
                    component: PlanWrite,
                },
                {
                    path: ":planId",
                    name: "PlanDetail",
                    component: PlanDetail,
                },
            ],
        },
        {
            path: "/board",
            component: BoardView,
            children: [
                {
                    path: ":pageNo?",
                    name: "BoardList",
                    component: BoardList,
                    props: (route) => ({
                        pageNo: route.params.pageNo ? parseInt(route.params.pageNo) : 1,
                        keyword: route.query.keyword || "",
                    }),
                },
                {
                    path: "write",
                    name: "BoardWrite",
                    component: BoardWrite,
                },
                {
                    path: ":boardNo/detail",
                    name: "BoardDetail",
                    component: BoardDetail,
                },
                {
                    path: ":boardNo/modify",
                    name: "BoardModify",
                    component: BoardModify,
                },
            ],
        },
        {
            path: "/mypage",
            component: MyPageView,
            children: [
                {
                    path: "",
                    name: "MyPage",
                    component: MyPage,
                },
            ],
        },
    ],
});

export default router;
