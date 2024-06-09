import axios from "axios";
import { reactive } from "vue";

const sidoState = reactive({
    sidoMap: new Map(),
    sidoList: [],
});

const getSido = async () => {
    try {
        const response = await axios.get("http://localhost/trip/sido");
        sidoState.sidoList = response.data;
        const map = new Map();
        response.data.forEach((item) => {
            map.set(item.sido_code, item.sido_name);
        });
        sidoState.sidoMap = map;
        return response.data;
    } catch (error) {
        console.error("Error fetching data:", error);
        throw error;
    }
};

const getCodeToTag = (sidoCode) => {
    return sidoState.sidoMap.get(sidoCode);
};

export { sidoState, getSido, getCodeToTag };
