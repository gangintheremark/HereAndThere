import axios from "axios";

const searchAttraction = (sido, gugun, type, keyword) => {
  return axios
    .post("http://localhost/trip/search", {
      sido: sido.value,
      gugun: gugun.value,
      type: type.value,
      keyword: keyword.value,
    })
    .then((response) => {
      return response.data;
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
      throw error;
    });
};

export { searchAttraction };
