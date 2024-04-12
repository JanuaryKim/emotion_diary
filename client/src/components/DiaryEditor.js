import { useState, useEffect, useRef, useContext, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import MyHeader from "./MyHeader";
import MyButton from "./MyButton";
import EmotionItem from "./EmotionItem";
import { getStrDate } from "../util/date";
import { emotionList } from "../util/emotion";
import { deleteDiary } from "../apis/deleteDiary";
import { putDiary } from "../apis/putDiary";
import { postDiary } from "../apis/postDiary";
import { useDropzone } from "react-dropzone";
import "../external.css";

const DiaryEditor = ({ isEdit, originData, id }) => {
  const contentRef = useRef();
  const [content, setContent] = useState("");
  const [emotion, setEmotion] = useState(3);
  const navigator = useNavigate();
  const [date, setDate] = useState(getStrDate(new Date()));

  // 이미지
  const [files, setFiles] = useState([]);
  const { getRootProps, getInputProps } = useDropzone({
    accept: {
      "image/*": [],
    },
    onDrop: (acceptedFiles) => {
      acceptedFiles.forEach((file, idx) => {
        const reader = new FileReader();

        reader.onabort = () => console.log("file reading was aborted");
        reader.onerror = () => console.log("file reading has failed");
        reader.onload = () => {
          // Do whatever you want with the file contents
          const binaryStr = reader.result;
          console.log(`${idx} 번째 이미지`);
          console.log(binaryStr);
        };
        reader.readAsArrayBuffer(file);
      });
      setFiles(
        acceptedFiles.map((file) =>
          Object.assign(file, {
            preview: URL.createObjectURL(file),
          })
        )
      );
    },
    maxFiles: 3,
    maxSize: 10000000, //10MB
    // maxSize: 500000,
    onError: (error) => {
      console.log("에러");
      console.log(error);
    },
    onDropRejected: (fileRejections) => {
      fileRejections.forEach((f) => {
        console.log("거부");
        console.log(f.errors);
      });
    },
  });
  const thumbs = files.map((file) => (
    <div className="thumb" key={file.name}>
      <div className="thumbInner">
        <img
          src={file.preview}
          className="thumbImg"
          // Revoke data uri after image is loaded
          onLoad={() => {
            console.log(file.preview);
            URL.revokeObjectURL(file.preview);
          }}
        />
      </div>
    </div>
  ));
  useEffect(() => {
    // Make sure to revoke the data uris to avoid memory leaks, will run on unmount
    return () => files.forEach((file) => URL.revokeObjectURL(file.preview));
  }, []);

  const handleClickEmotion = useCallback((emotionId) => {
    setEmotion(emotionId);
  }, []);

  const handleRemove = async () => {
    if (window.confirm(`정말 삭제할까요?`)) {
      //!삭제
      const res = await deleteDiary(id);
      navigator(`/`, { replace: true });
    }
  };

  const handleClickSubmit = async () => {
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
        //!수정
        const formData = new FormData();

        const diaryData = {
          regDate: date + ` 00:00`,
          emotion: emotion,
          content: content,
        };
        const diaryDto = JSON.stringify(diaryData);
        formData.append(
          "diary-put-dto",
          new Blob([diaryDto], { type: "application/json" })
        );

        for (const imageFile of files) {
          formData.append("diary-images", imageFile);
        }

        const res = await putDiary(id, "multipart/form-data", formData);
      } else {
        //!생성
        const formData = new FormData();

        const diaryData = {
          regDate: date + ` 00:00`,
          emotion: emotion,
          content: content,
        };
        const diaryDto = JSON.stringify(diaryData);
        formData.append(
          "diary-post-dto",
          new Blob([diaryDto], { type: "application/json" })
        );

        for (const imageFile of files) {
          formData.append("diary-images", imageFile);
        }

        const res = await postDiary("multipart/form-data", formData);
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

        {/* 이미지 */}
        <section>
          <div {...getRootProps({ className: "dropzone" })}>
            <input {...getInputProps()} />
            <p>파일을 드래그 하여 올리거나 선택하여 주세요 (3개)</p>
          </div>
          <aside className="thumbsContainer">{thumbs}</aside>
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
