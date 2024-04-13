import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

// oauth 로그인 후 액세스 토큰을 저장하는 중간 페이지
const SaveToken = () => {
  console.log("세이브 토큰");
  const navigator = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);

    localStorage.setItem(
      "access_token",
      JSON.stringify(params.get("access_token"))
    );

    navigator("/", { replace: true });
  });
};

export default SaveToken;
