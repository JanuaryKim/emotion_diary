import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { DiaryStateContext } from "../App";
import DiaryEditor from "../components/DiaryEditor";
import LoginHeader from "../components/LoginHeader";
import { getDiaryDetail } from "../apis/getDiaryDetail";
import {
  getMappingDiaryDetailFromServer,
  getMappingDiaryDetailFromLocal,
} from "../util/mapping";

const Edit = () => {
  const [originData, setOriginData] = useState();
  const { id } = useParams();
  const { login, localData } = useContext(DiaryStateContext);

  const getDiary = async (id) => {
    const diaryDetailData = await getDiaryDetail(id);

    const mappedData = getMappingDiaryDetailFromServer(diaryDetailData);

    setOriginData(mappedData);
  };

  const getDiaryLocal = (id) => {
    const targetDiary = localData.find(
      (it) => parseInt(it.id) === parseInt(id)
    );

    const mappedData = getMappingDiaryDetailFromLocal(targetDiary);

    console.log("수정 페이지에 넘어갈 데이터");
    console.log(mappedData);
    console.log(id);
    setOriginData(mappedData);
  };

  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `${id}번 일기 수정`;
  }, []);

  useEffect(() => {
    if (login) {
      getDiary(id);
    } else {
      getDiaryLocal(id);
    }
  }, [id, localData]);

  return (
    <div>
      <LoginHeader />
      {originData && (
        <DiaryEditor isEdit={true} originData={originData} id={id} />
      )}
    </div>
  );
};

export default Edit;
