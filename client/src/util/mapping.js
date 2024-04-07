export const getMappingDiaryList = (list) => {
  return list.map((it) => {
    return {
      id: it.diaryId,
      date: new Date(it.regDate).getTime(),
      content: it.content,
      emotion: it.emotion,
    };
  });
};
