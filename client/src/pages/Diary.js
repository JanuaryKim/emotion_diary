import { useNavigate, useParams } from "react-router-dom";
import { DiaryStateContext } from "../App";
import { useContext, useEffect, useState } from "react";
import MyHeader from "../components/MyHeader";
import { getStrDate } from "../util/date";
import MyButton from "../components/MyButton";
import { emotionList } from "../util/emotion";
import LoginHeader from "../components/LoginHeader";

const Diary = () => {
  const { id } = useParams();
  const { diaryList, dataId } = useContext(DiaryStateContext);
  const navigate = useNavigate();
  const [data, setData] = useState();

  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `감정 일기장 - ${id}번 일기`;
  }, []);

  useEffect(() => {
    if (diaryList.length >= 1) {
      const targetDiary = diaryList.find(
        (it) => parseInt(it.id) === parseInt(id)
      );

      if (targetDiary) {
        setData(targetDiary);
      } else {
        alert("존재하지 않는 일기입니다");
        navigate("/", { replace: true });
      }
    }
  }, [id, diaryList]);

  if (!data) {
    return (
      <div className="DiaryPage">
        <h2>로딩중입니다....</h2>
      </div>
    );
  } else {
    const curEmotionData = emotionList.find(
      (emotion) => parseInt(emotion.emotion_id) === parseInt(data.emotion)
    );

    console.log(curEmotionData);

    return (
      <div className="DiaryPage">
        <LoginHeader />
        <MyHeader
          headText={getStrDate(new Date(data.date)) + ` 기록`}
          leftChild={
            <MyButton text={"<뒤로가기"} onClick={() => navigate(-1)} />
          }
          rightChild={
            <MyButton
              text={"수정하기"}
              onClick={() => navigate(`/edit/${data.id}`)}
            />
          }
        />
        <article>
          <section>
            <h4>오늘의 감정</h4>
            <div
              className={[
                "diary_img_wrapper",
                `diary_img_wrapper_${data.emotion}`,
              ].join(" ")}
            >
              <img src={curEmotionData.emotion_img} />
              <div className="emotion_descript">
                {curEmotionData.emotion_text}
              </div>
            </div>
          </section>
          <section>
            <h4>오늘의 일기</h4>
            <div className="diary_content_wrapper">
              <p>{data.content}</p>
            </div>
          </section>
        </article>
      </div>
    );
  }
};

export default Diary;
