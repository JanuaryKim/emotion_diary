import axios from "axios";

const baseUrl = process.env.REACT_APP_API_BASE_URL;
const Api = axios.create({
  baseURL: baseUrl,
});

Api.interceptors.request.use((config) => {
  const accessToken = localStorage.getItem("access_token");
  config.headers["Authorization"] = JSON.parse(accessToken); //따옴표 제거 위해
  return config;
});

export const getDiaryPageData = async (query) => {
  try {
    const diaryPageData = await Api.get(`/api/diarys?${query}`);
    return diaryPageData.data;
  } catch (err) {
    console.log("Error >>", err);
  }
};
