import { useNavigate } from "react-router-dom";
import { useEffect, useContext } from "react";
import { DiaryDispatchContext } from "../App";
import Reset from "./Reset";
// oauth 로그인 후 액세스 토큰을 저장하는 중간 페이지
const SaveToken = () => {
  const navigator = useNavigate();
  const { setLogin } = useContext(DiaryDispatchContext);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);

    localStorage.setItem(
      "access_token",
      JSON.stringify(params.get("access_token"))
    );
    setLogin(true);
    navigator("/Reset", { replace: true });
  });
};

export default SaveToken;
