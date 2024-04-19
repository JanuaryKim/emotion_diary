export const reducer = (state, action) => {
  const getAllData = async () => {
    await action.getAll();
  };

  let newState;
  switch (action.type) {
    case "INIT":
      return action.data;
    case "CREATE":
      action
        .add(action.data)
        .then((event) => console.log(`${event} 일기 추가`));
      newState = [...state, action.data];
      console.log("새로운 상태");
      console.log(newState);
      break;
    case "EDIT":
      // newState = state.map((it) => {
      //   return action.targetId === it.id ? { ...action.data } : it;
      // });

      console.log("수정 데이터");
      console.log(action.data);
      action
        .update(action.data)
        .then((event) => console.log(`${event} 일기 수정`));
      newState = state.map((it) =>
        action.data.id !== it.id ? it : action.data
      );

      break;
    case "REMOVE":
      newState = state.filter((it) => it.id !== action.targetId);
      break;
    default:
      return state;
  }

  return newState;
};
