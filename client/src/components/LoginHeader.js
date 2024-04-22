import LoginButton from "./LoginButton";
import LogoutButton from "./LogoutButton";

//테스트
const socialType = [
  {
    socialEngName: `google`,
    socialKorName: `구글`,
    color: `#ffffff`,
  },
  {
    socialEngName: `kakao`,
    socialKorName: `카카오`,
    color: `#fada0b`,
  },
];

const loginButtons = () => {
  return socialType.map((socialObj, idx) => {
    return <LoginButton key={idx} social={socialObj} />;
  });
};

const logoutButton = () => {
  return <LogoutButton />;
};

const stateLabel = (labelString, className) => {
  return <button className={className}>{labelString}</button>;
};

const LoginHeader = () => {
  return (
    <div className="LoginHeader">
      <div className="btn_left">
        {!localStorage.getItem("access_token")
          ? stateLabel("비로그인 모드", "StateLogout")
          : stateLabel("로그인 모드", "StateLogin")}
      </div>
      <div className="btn_right">
        {!localStorage.getItem("access_token")
          ? loginButtons()
          : logoutButton()}
      </div>
    </div>

    // <div>
    //   {socialType.map((socialObj, idx) => {
    //     return <LoginButton key={idx} social={socialObj} />;
    //   })}
    // </div>
  );
};

export default LoginHeader;
