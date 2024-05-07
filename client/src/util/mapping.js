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
      id: it.id,
      date: new Date(it.date).getTime(),
      content: it.content,
      emotion: it.emotion,
      image: it.images,
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

export const getMappingDiaryImages = (images) => {
  return images.map((it) => {
    return {
      originalFileName: it.name,
      url: it.base64URL,
    };
  });
};
