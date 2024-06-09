import { reactive } from "vue";
import axios from "axios";

const userState = reactive({
    userInfo: {},
});

const getUserInfo = () => {
    const storedUserInfo = sessionStorage.getItem("userInfo");
    if (storedUserInfo) userState.userInfo = JSON.parse(storedUserInfo);
};

const modifyMyPage = (userId, userName, userPwd, email, imageFile) => {
    const formData = new FormData();
    const member = {
        userId: userId,
        userName: userName,
        userPwd: userPwd,
        email: email,
    };

    formData.append("member", new Blob([JSON.stringify(member)], { type: "application/json" }));

    if (imageFile) {
        formData.append("image", imageFile);
    }

    axios
        .put("http://localhost/user/", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        })
        .then((response) => {
            const userInfo = response.data;
            sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
            getUserInfo();
            return response.data;
        })
        .catch((error) => {
            console.error("Error fetching data:", error);
            throw error;
        });
};

export { userState, getUserInfo, modifyMyPage };
