import Api from "./Api";

export const getDiaryPageData = async (query) => {
  try {
    const diaryPageData = await Api.get(`/api/diarys?${query}`);
    return diaryPageData.data;
  } catch (err) {
    console.log("Error >>", err);
  }
};
