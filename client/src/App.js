import React, { useReducer, useRef, useEffect } from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import New from "./pages/New";
import Diary from "./pages/Diary";
import Edit from "./pages/Edit";
import Home from "./pages/Home";
import SaveToken from "./pages/SaveToken";

export const DiaryStateContext = React.createContext(null);
export const DiaryDispatchContext = React.createContext(null);

function App() {
  return (
    <DiaryStateContext.Provider>
      <DiaryDispatchContext.Provider>
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
