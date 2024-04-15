export const getMappingDiaryListFromServer = (list) => {
  return list.map((it) => {
    return {
      id: it.diaryId,
      date: new Date(it.regDate).getTime(),
      content: it.content,
      emotion: it.emotion,
    };
  });
};

export const getMappingDiaryListFromLocal = (list) => {
  return list.map((it) => {
    return {
      id: it.diaryId,
      date: new Date(it.date).getTime(),
      content: it.content,
      emotion: it.emotion,
    };
  });
};

export const getMappingDiaryDetail = (it) => {
  return {
    id: it.diaryId,
    date: new Date(it.regDate).getTime(),
    content: it.content,
    emotion: it.emotion,
    images: it.images,
  };
};
