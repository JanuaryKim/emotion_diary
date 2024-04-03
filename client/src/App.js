import React, { useReducer, useRef, useEffect } from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import New from "./pages/New";
import Diary from "./pages/Diary";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import SaveToken from "./pages/SaveToken";

const reducer = (state, action) => {
  let newState = [];
  switch (action.type) {
    case "INIT":
      return action.data;
    case "CREATE":
      newState = [action.data, ...state];
      break;
    case "EDIT":
      newState = state.map((it) =>
        it.id === action.targetId ? { ...action.data } : it
      );
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

export const DiaryStateContext = React.createContext(null);
export const DiaryDispatchContext = React.createContext(null);

function App() {
  const [diaryList, dispatch] = useReducer(reducer, []);

  const dataId = useRef(1);

  useEffect(() => {
    const localData = JSON.parse(localStorage.getItem(`diary`));

    if (localData) {
      dispatch({
        type: "INIT",
        data: localData,
      });
      const diaryData = localData.sort((a, b) => b.id - a.id);
      if (diaryData.length > 0) dataId.current = diaryData[0].id + 1;
    }
  }, []);

  const onCreate = (date, content, emotion) => {
    dispatch({
      type: "CREATE",
      data: {
        id: dataId.current++,
        date: new Date(date).getTime(),
        content,
        emotion,
      },
    });
  };

  const onEdit = (targetId, date, content, emotion) => {
    dispatch({
      type: "EDIT",
      targetId,
      data: {
        id: targetId,
        date: new Date(date).getTime(),
        content,
        emotion,
      },
    });
  };

  const onRemove = (targetId) => {
    dispatch({
      type: "REMOVE",
      targetId,
    });
  };

  return (
    <DiaryStateContext.Provider value={{ diaryList, dataId }}>
      <DiaryDispatchContext.Provider value={{ onCreate, onEdit, onRemove }}>
        <BrowserRouter>
          <div className="App">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/new" element={<New />} />
              <Route path="/diary/:id" element={<Diary />} />
              <Route path="/edit/:id" element={<Edit />} />
              <Route path="/saveToken" element={<SaveToken />} />
            </Routes>
          </div>
        </BrowserRouter>
      </DiaryDispatchContext.Provider>
    </DiaryStateContext.Provider>
  );
}

export default App;
