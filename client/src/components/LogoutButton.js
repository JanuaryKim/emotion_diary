import { useNavigate } from "react-router-dom";
import { useContext } from "react";
import { DiaryDispatchContext } from "../App";
import Reset from "../pages/Reset";
const LogoutButton = () => {
  const { setLogin } = useContext(DiaryDispatchContext);
  const navigator = useNavigate();
  const logout = () => {
    localStorage.removeItem("access_token");
    setLogin(false);
    navigator("/Reset", { replace: true });
  };

  return (
    <button className="MyButton LogoutButton" onClick={() => logout()}>
      로그아웃
    </button>
  );
};

export default LogoutButton;
