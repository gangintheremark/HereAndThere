import axios from "axios";
import router from "@/router";
// Get
const getBestReviews = () => {
    return axios
        .get(`http://localhost/review/search/best`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getRecentReviews = (sidoCode) => {
    return axios
        .get(`http://localhost/review/search/${sidoCode}/recent`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getPopularReviews = (sidoCode) => {
    return axios
        .get(`http://localhost/review/search/${sidoCode}/likes`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

const getReviewDetail = (reviewId, userId) => {
    userId = userId == undefined ? "null" : userId;
    return axios
        .get(`http://localhost/review/search/${reviewId}`, {
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

//Post
const createReview = (userId, title, content, sidoCode, starRating, imageFile) => {
    const formData = new FormData();
    const review = {
        userId: userId,
        title: title,
        content: content,
        sidoCode: sidoCode,
        starRating: starRating,
    };
    formData.append("review", new Blob([JSON.stringify(review)], { type: "application/json" }));

    if (imageFile) {
        formData.append("image", imageFile);
    }

    axios
        .post("http://localhost/review/", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
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

const LikeReview = (reviewId, userId) => {
    const body = {
        reviewId: reviewId,
        userId: userId,
    };
    axios.post("http://localhost/review/like", body).catch((error) => {
        console.error("Error fetching data:", error);
        return error;
    });
};

const cancleLike = (reviewId, userId) => {
    axios
        .delete(`http://localhost/review/like/${reviewId}`, {
            params: {
                userId: userId,
            },
        })
        .then((response) => {
            console.log(response);
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            return error;
        });
};

//Delete
const removeReview = (reviewId) => {
    axios
        .delete(`http://localhost/review/${reviewId}`)
        .then((response) => {
            router.push({ name: "ReviewList" });
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            return error;
        });
};

export {
    getBestReviews,
    getPopularReviews,
    getRecentReviews,
    getReviewDetail,
    createReview,
    LikeReview,
    removeReview,
    cancleLike,
};
