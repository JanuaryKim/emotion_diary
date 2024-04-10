import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { DiaryStateContext } from "../App";
import DiaryEditor from "../components/DiaryEditor";
import LoginHeader from "../components/LoginHeader";
import { getDiaryDetail } from "../apis/getDiaryDetail";
import { getMappingDiaryDetail } from "../util/mapping";

const Edit = () => {
  const [originData, setOriginData] = useState();
  const { id } = useParams();

  const getDiary = async (id) => {
    const diaryDetailData = await getDiaryDetail(id);
    const mappedData = getMappingDiaryDetail(diaryDetailData);

    setOriginData(mappedData);
  };

  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `${id}번 일기 수정`;
  }, []);

  useEffect(() => {
    if (localStorage.getItem("access_token")) {
      getDiary(id);
    } else {
    }
  }, [id]);

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
