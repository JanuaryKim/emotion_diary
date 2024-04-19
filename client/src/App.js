import React, { useState, useReducer, useRef, useEffect } from "react";
import { IndexedDBConfig } from "./config/IndexedDBConfig";
import { initDB, useIndexedDB } from "react-indexed-db-hook";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import New from "./pages/New";
import Diary from "./pages/Diary";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import SaveToken from "./pages/SaveToken";
import Reset from "./pages/Reset";
// import { reducer } from "./util/localReducer";
import { getMappingDiaryImages } from "./util/mapping";
export const DiaryStateContext = React.createContext(null);
export const DiaryDispatchContext = React.createContext(null);

initDB(IndexedDBConfig);

function App() {
  const [login, setLogin] = useState(
    localStorage.getItem("access_token") === null ? false : true
  );
  const [localData, setLocalData] = useState([]);
  const { getAll, add, update } = useIndexedDB("diary");

  //로그인 여부에 따라 로컬 데이터 init과 로컬 데이터 삭제의 기능을 함.

  const initFromLocalData = () => {
    getAll().then((emotionDiary) => {
      setLocalData(emotionDiary);
    });
  };
  useEffect(() => {
    if (!login) {
      initFromLocalData();
    } else {
      setLocalData([]);
    }
  }, [login]);

  const onCreate = (date, content, emotion, images) => {
    const mappingImgData = getMappingDiaryImages(images);

    const newDiary = {
      date: new Date(date).getTime(),
      content,
      emotion,
      images: mappingImgData,
    };

    add(newDiary);
    getAll().then((emotionDiary) => setLocalData(emotionDiary));
  };

  const onEdit = (id, date, content, emotion, images) => {
    console.log("업데이트할 이미지");
    console.log(images);
    const mappingImgData = getMappingDiaryImages(images);

    const updateDiary = {
      id: id,
      date: new Date(date).getTime(),
      content,
      emotion,
      images: mappingImgData,
    };
    update(updateDiary);
    getAll().then((emotionDiary) => setLocalData(emotionDiary));
  };

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
              <Route path="/Reset" element={<Reset />} />
            </Routes>
          </div>
        </BrowserRouter>
      </DiaryDispatchContext.Provider>
    </DiaryStateContext.Provider>
  );
}

export default App;
