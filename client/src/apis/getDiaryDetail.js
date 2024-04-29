import Api from "./Api";

export const getDiaryDetail = async (diaryId) => {
  try {
    const diaryDetailData = await Api.get(`/api/diarys/${diaryId}`);
    return diaryDetailData.data;
  } catch (err) {
    console.log("Error >>", err);
  }
};
