import { useNavigate, useParams } from "react-router-dom";
import { DiaryStateContext } from "../App";
import { useContext, useEffect, useState } from "react";
import MyHeader from "../components/MyHeader";
import { getStrDate } from "../util/date";
import MyButton from "../components/MyButton";
import { emotionList } from "../util/emotion";
import LoginHeader from "../components/LoginHeader";
import { getDiaryDetail } from "../apis/getDiaryDetail";
import { getMappingDiaryDetail } from "../util/mapping";
import ImageGallery from "react-image-gallery";
import "react-image-gallery/styles/css/image-gallery.css";

const Diary = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [data, setData] = useState();

  const imgs = [
    {
      original: "https://picsum.photos/id/1018/1000/600/",
      thumbnail: "https://picsum.photos/id/1018/250/150/",
    },
    {
      original: "https://picsum.photos/id/1015/1000/600/",
      thumbnail: "https://picsum.photos/id/1015/250/150/",
    },
    {
      original: "https://picsum.photos/id/1019/1000/600/",
      thumbnail: "https://picsum.photos/id/1019/250/150/",
    },
  ];

  const getDiary = async (id) => {
    const diaryDetailData = await getDiaryDetail(id);
    const mappingData = getMappingDiaryDetail(diaryDetailData);
    console.log(mappingData.images);
    setData(mappingData);
  };

  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `감정 일기장 - ${id}번 일기`;
  }, []);

  useEffect(() => {
    if (localStorage.getItem("access_token")) {
      getDiary(id);
    } else {
    }
  }, [id]);

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
            <h4>오늘의 순간</h4>
            <div className="diary_image_wrapper">
              {data.images.length > 0 ? (
                <ImageGallery
                  items={data.images.map((url) => {
                    return {
                      original: process.env.REACT_APP_API_BASE_URL + url,
                      thumbnail: process.env.REACT_APP_API_BASE_URL + url,
                    };
                  })}
                />
              ) : (
                "이미지가 존재하지 않습니다"
              )}
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
