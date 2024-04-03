import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { DiaryStateContext } from "../App";
import DiaryEditor from "../components/DiaryEditor";
import LoginHeader from "../components/LoginHeader";

const Edit = () => {
  const [originData, setOriginData] = useState();
  const navigator = useNavigate();
  const { id } = useParams();
  const { diaryList } = useContext(DiaryStateContext);

  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `${id}번 일기 수정`;
  }, []);

  useEffect(() => {
    if (diaryList.length >= 1) {
      const targetDiary = diaryList.find(
        (it) => parseInt(it.id) === parseInt(id)
      );
      console.log(targetDiary);

      if (targetDiary) {
        setOriginData(targetDiary);
      } else {
        alert("존재하지 않는 일기입니다");
        navigator("/", { replace: true });
      }
    }
  }, [id, diaryList]);

  return (
    <div>
      <LoginHeader />
      {originData && <DiaryEditor isEdit={true} originData={originData} />}
    </div>
  );
};

export default Edit;
