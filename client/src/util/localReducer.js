export const reducer = (state, action) => {
  let newState = [];
  switch (action.type) {
    case "INIT":
      return action.data;
    case "CREATE":
      newState = [action.data, ...state];
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

  localStorage.setItem("diary", JSON.stringify(newState));
  return newState;
};
