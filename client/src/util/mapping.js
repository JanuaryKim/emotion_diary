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

export const getMappingDiaryDetailFromServer = (it) => {
  return {
    id: it.diaryId,
    date: new Date(it.regDate).getTime(),
    content: it.content,
    emotion: it.emotion,
    images: it.images,
  };
};

export const getMappingDiaryDetailFromLocal = (it) => {
  return {
    id: it.id,
    date: new Date(it.date).getTime(),
    content: it.content,
    emotion: it.emotion,
    images: it.images,
  };
};
