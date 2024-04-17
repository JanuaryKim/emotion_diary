import React, { useState, useReducer, useRef, useEffect } from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import New from "./pages/New";
import Diary from "./pages/Diary";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import SaveToken from "./pages/SaveToken";
import Test from "./pages/Test";
import Reset from "./pages/Reset";
import { reducer } from "./util/localReducer";
import { type } from "@testing-library/user-event/dist/type";
export const DiaryStateContext = React.createContext(null);
export const DiaryDispatchContext = React.createContext(null);

function App() {
  const [login, setLogin] = useState(
    localStorage.getItem("access_token") === null ? false : true
  );
  const [localData, setLocalData] = useReducer(reducer, []);
  const dataId = useRef(1);

  //로그인 여부에 따라 로컬 데이터 init과 로컬 데이터 삭제의 기능을 함.
  useEffect(() => {
    if (!login) {
      const storageData = JSON.parse(localStorage.getItem("diary"));
      let localData = [];
      if (storageData !== null) {
        dataId.current = storageData[0].id + 1;
        localData = storageData;
      }
      setLocalData({
        type: "INIT",
        data: localData,
      });
    } else {
      setLocalData({
        type: "INIT",
        data: [],
      });
    }
  }, [login]);

  const onCreate = (date, content, emotion, images) => {
    const mappingImgData = images.map((it) => {
      return {
        originalFileName: it.name,
        url: it.base64URL,
      };
    });

    setLocalData({
      type: "CREATE",
      data: {
        id: dataId.current++,
        date: new Date(date).getTime(),
        content,
        emotion,
        images: mappingImgData,
      },
    });
  };

  const onEdit = (id, date, content, emotion, images) => {};

  return (
    <DiaryStateContext.Provider value={{ login, localData }}>
      <DiaryDispatchContext.Provider value={{ setLogin, onCreate, onEdit }}>
        <BrowserRouter>
          <div className="App">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/new" element={<New />} />
              <Route path="/diary/:id" element={<Diary />} />
              <Route path="/edit/:id" element={<Edit />} />
              <Route path="/saveToken" element={<SaveToken />} />
              <Route path="/Test" element={<Test />} />
              <Route path="/Reset" element={<Reset />} />
            </Routes>
          </div>
        </BrowserRouter>
      </DiaryDispatchContext.Provider>
    </DiaryStateContext.Provider>
  );
}

export default App;
