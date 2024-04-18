export const reducer = (state, action) => {
  let newState;
  switch (action.type) {
    case "INIT":
      return action.data;
    case "CREATE":
      action
        .add(action.data)
        .then((event) => console.log(`${event} 일기 추가`));
      newState = [...state, action.data];
      break;
    case "EDIT":
      newState = state.map((it) => {
        return action.targetId === it.id ? { ...action.data } : it;
      });
      break;
    case "REMOVE":
      newState = state.filter((it) => it.id !== action.targetId);
      break;
    default:
      return state;
  }

  return newState;
};
