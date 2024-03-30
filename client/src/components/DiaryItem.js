import MyButton from "./MyButton";
import { useNavigate } from "react-router-dom";
import React from "react";
const DiaryItem = ({ id, content, emotion, date }) => {
  const navigator = useNavigate();

  const goDetail = () => {
    navigator(`/diary/${id}`);
  };

  const goEdit = () => {
    navigator(`/edit/${id}`);
  };

  const strDate = new Date(date).toLocaleDateString();
  return (
    <div className="DiaryItem">
      <div
        onClick={goDetail}
        className={[
          `emotion_img_wrapper`,
          `emotion_img_wrapper_${emotion}`,
        ].join(" ")}
      >
        <img
          src={process.env.PUBLIC_URL + `assets/emotion${emotion}.png`}
        ></img>
      </div>
      <div onClick={goDetail} className="info_wrapper">
        <div className="diary_date">{strDate}</div>
        <div className="diary_content_preview">{content.slice(0, 25)}</div>
      </div>
      <div className="btn_wrapper">
        <MyButton onClick={goEdit} text={"수정하기"} />
      </div>
    </div>
  );
};

export default React.memo(DiaryItem);
