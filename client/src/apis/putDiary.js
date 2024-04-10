import Api from "./Api";

export const putDiary = async (id, contentType, data) => {
  try {
    if (contentType) {
      Api.interceptors.request.use((config) => {
        config.headers["Content-Type"] = contentType;
        return config;
      });
    }
    const response = await Api.put(`/api/diarys/${id}`, data);
    return response.status;
  } catch (err) {
    console.log("Error >>", err);
  }
};
