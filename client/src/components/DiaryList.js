import React, { useState, useEffect } from "react";
import MyButton from "./MyButton";
import { useNavigate } from "react-router-dom";
import DiaryItem from "./DiaryItem";

const optionList = [
  { value: "latest", name: "최신순" },
  { value: "oldest", name: "오래된순" },
];

const filterList = [
  { value: "all", name: "모든 것" },
  { value: "good", name: "좋은 기분만" },
  { value: "bad", name: "안 좋은 기분만" },
];

const ControlMenu = React.memo(({ sortType, onChange, optionList }) => {
  return (
    <select
      className="ControlMenu"
      value={sortType}
      onChange={(e) => onChange(e.target.value)}
    >
      {optionList.map((op, idx) => {
        return (
          <option key={idx} value={op.value}>
            {op.name}
          </option>
        );
      })}
    </select>
  );
});

const DiaryList = ({ diaryList, sortType, setSortType, filter, setFilter }) => {
  const navigate = useNavigate();

  return (
    <div className="DiaryList">
      <div className="menu_wrapper">
        <div className="left_col">
          <ControlMenu
            sortType={sortType}
            onChange={setSortType}
            optionList={optionList}
          ></ControlMenu>
          <ControlMenu
            sortType={filter}
            onChange={setFilter}
            optionList={filterList}
          ></ControlMenu>
        </div>
        <div className="right_col">
          <MyButton
            text={"새 일기 작성"}
            type={"positive"}
            onClick={() => navigate("/new")}
          />
        </div>
      </div>

      {diaryList.map((it) => {
        return <DiaryItem key={it.id} {...it} />;
      })}
    </div>
  );
};

export default DiaryList;
