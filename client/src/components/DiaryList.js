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

const DiaryList = ({ diaryList }) => {
  const navigate = useNavigate();
  const [sortType, setSortType] = useState("latest");
  const [filter, setFilter] = useState("all");

  const getDiaryList = () => {
    const filtering = (it) => {
      if (filter === "good") {
        return it.emotion <= 3;
      } else {
        return it.emotion > 3;
      }
    };

    const compare = (d1, d2) => {
      if (sortType === "latest") {
        return parseInt(d2.date) - parseInt(d1.date); //문자열로 들어올지도 몰라서 parseInt
      } else {
        return parseInt(d1.date) - parseInt(d2.date);
      }
    };
    const copyList = JSON.parse(JSON.stringify(diaryList)); //일기리스트 깊은 복사

    const filtedList = filter === "all" ? copyList : copyList.filter(filtering);

    return filtedList.sort(compare);
  };

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

      {getDiaryList().map((it) => {
        return <DiaryItem key={it.id} {...it} />;
      })}
    </div>
  );
};

DiaryList.defaultProps = {
  diaryList: [
    {
      id: 0,
      date: 2208439681845,
      content: "기본 데이터",
      emotion: 3,
    },
  ],
};

export default DiaryList;
