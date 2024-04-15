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

  useEffect(() => {
    console.log("App 생성자");

    if (!login) {
      const localData = JSON.parse(localStorage.getItem("diary"));
      setLocalData({
        type: "INIT",
        data: localData,
      });
      console.log("App : ");
      console.log(localData);
    } else {
      setLocalData({
        type: "INIT",
        data: [],
      });
    }
  }, [login]);

  return (
    <DiaryStateContext.Provider value={{ login, localData }}>
      <DiaryDispatchContext.Provider value={{ setLogin }}>
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
