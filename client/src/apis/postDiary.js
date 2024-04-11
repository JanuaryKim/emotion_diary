import Api from "./Api";

export const postDiary = async (contentType, data) => {
  try {
    if (contentType) {
      Api.interceptors.request.use((config) => {
        config.headers["Content-Type"] = contentType;
        return config;
      });
    }

    const response = await Api.post(`/api/diarys`, data);
    return response.status;
  } catch (err) {
    console.log("Error >>", err);
  }
};
