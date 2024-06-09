import axios from "axios";
import router from "@/router";

/********************** GET ***********************/
const getBestPlans = () => {
    return axios
        .get(`http://localhost/plan/search/best`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getRecentPlans = (sidoCode) => {
    return axios
        .get(`http://localhost/plan/search/${sidoCode}/recent`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getPopularPlans = (sidoCode) => {
    return axios
        .get(`http://localhost/plan/search/${sidoCode}/likes`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getPlanDetail = (planId, userId) => {
    userId = userId == undefined ? "null" : userId;
    return axios
        .get(`http://localhost/plan/search/${planId}`, {
            params: {
                userId: userId,
            },
        })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

/********************** POST ***********************/
const createPlan = (userId, title, distance, sidoCode, placesId) => {
    const body = {
        plan: {
            userId: userId,
            title: title,
            distance: distance,
            sidoCode: sidoCode,
        },
        places: placesId.map((contentId, index) => ({
            contentId: contentId,
        })),
    };
    console.log(body);
    axios
        .post("http://localhost/plan/", body)
        .then((response) => {
            router.push({ name: "PlanList" });
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const LikePlan = (planId, userId) => {
    const body = {
        planId: planId,
        userId: userId,
    };
    axios.post("http://localhost/plan/like", body).catch((error) => {
        console.error("Error fetching data:", error);
        return error;
    });
};

const cancleLike = (planId, userId) => {
    axios
        .delete(`http://localhost/plan/like/${planId}`, {
            params: {
                userId: userId,
            },
        })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            return error;
        });
};

/********************** Delete ***********************/
const removePlan = (planId) => {
    axios
        .delete(`http://localhost/plan/${planId}`)
        .then((response) => {
            router.push({ name: "PlanList" });
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            return error;
        });
};

export {
    getBestPlans,
    getRecentPlans,
    getPopularPlans,
    getPlanDetail,
    createPlan,
    LikePlan,
    cancleLike,
    removePlan,
};
