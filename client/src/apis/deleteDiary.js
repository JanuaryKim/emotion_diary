import Api from "./Api";

export const deleteDiary = async (id) => {
  try {
    const response = await Api.delete(`/api/diarys/${id}`);
    return response.status;
  } catch (err) {
    console.log("Error >>", err);
  }
};
