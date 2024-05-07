//총 페이지수 계산
export const getTotalPageCnt = (diaryTotalCount, pageSize) => {
  return diaryTotalCount == 0
    ? 1
    : diaryTotalCount % pageSize == 0
    ? diaryTotalCount / pageSize
    : Math.floor(diaryTotalCount / pageSize + 1);
};
