import { useNavigate } from "react-router-dom";

const LoginButton = ({ social }) => {
  const navigator = useNavigate();
  const login = (socialEngName) => {
    const baseURL = process.env.REACT_APP_API_BASE_URL;
    window.location.assign(`${baseURL}/oauth2/authorization/${socialEngName}`);

    //홈으로 보내는데, 홈으로 갔을 때, 뒤로가기로 일기작성 페이지로 돌아오지 못하게 두번째 인자로 옵션을 줌
    // navigator("/", { replace: true });
  };

  return (
    <button onClick={() => login(social.socialEngName)}>
      {social.socialKorName} 로그인
    </button>
  );
};

export default LoginButton;
