import { useState, useEffect, useRef, useContext, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import MyHeader from "./MyHeader";
import MyButton from "./MyButton";
import EmotionItem from "./EmotionItem";
import { DiaryDispatchContext } from "../App";
import { getStrDate } from "../util/date";
import { emotionList } from "../util/emotion";

const DiaryEditor = ({ isEdit, originData }) => {
  const contentRef = useRef();
  const [content, setContent] = useState("");
  const [emotion, setEmotion] = useState(3);
  const navigator = useNavigate();
  const [date, setDate] = useState(getStrDate(new Date()));
  const { onCreate, onEdit, onRemove } = useContext(DiaryDispatchContext);

  const handleClickEmotion = useCallback((emotionId) => {
    setEmotion(emotionId);
  }, []);

  const handleRemove = () => {
    if (window.confirm(`정말 삭제할까요?`)) {
      onRemove(originData.id);
      navigator(`/`, { replace: true });
    }
  };

  const handleClickSubmit = () => {
    if (content.length < 1) {
      contentRef.current.focus();
      return;
    }

    if (
      window.confirm(
        isEdit ? `${originData.id}번 일기를 수정할까요?` : `일기를 등록할까요?`
      )
    ) {
      if (isEdit) {
        onEdit(originData.id, date, content, emotion);
      } else {
        onCreate(date, content, emotion);
      }
    }
    navigator("/", { replace: true }); //홈으로 보내는데, 홈으로 갔을 때, 뒤로가기로 일기작성 페이지로 돌아오지 못하게 두번째 인자로 옵션을 줌
  };

  useEffect(() => {
    if (isEdit) {
      setDate(getStrDate(new Date(parseInt(originData.date))));
      setEmotion(originData.emotion);
      setContent(originData.content);
    }
  }, []);

  return (
    <div className="DiaryEditor">
      <MyHeader
        headText={isEdit ? "일기 수정하기" : "새로운 일기 쓰기"}
        leftChild={<MyButton text={"뒤로가기"} onClick={() => navigator(-1)} />}
        rightChild={
          isEdit && (
            <MyButton
              text={"삭제하기"}
              onClick={handleRemove}
              type={"negative"}
            />
          )
        }
      />

      <div>
        <section>
          <h4>오늘은 언제인가요?</h4>
          <div className="input_box">
            <input
              className="input_date"
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
            ></input>
          </div>
        </section>
        <section>
          <h4>오늘의 감정</h4>
          <div className="input_box emotion_list_wrapper">
            {emotionList.map((it) => {
              return (
                <EmotionItem
                  key={it.emotion_id}
                  {...it}
                  onClick={handleClickEmotion}
                  isSelected={emotion === it.emotion_id}
                />
              );
            })}
          </div>
        </section>
        <section>
          <h2>오늘의 일기</h2>
          <div className="input_box text_wrapper">
            <textarea
              placeholder="오늘은 어땟나요"
              onChange={(e) => setContent(e.target.value)}
              ref={contentRef}
              value={content}
            ></textarea>
          </div>
        </section>

        <section>
          <div className="control_box">
            <MyButton text={"취소하기"} onClick={() => navigator(-1)} />
            <MyButton
              text={"작성 완료"}
              type={"positive"}
              onClick={handleClickSubmit}
            />
          </div>
        </section>
      </div>
    </div>
  );
};

export default DiaryEditor;
