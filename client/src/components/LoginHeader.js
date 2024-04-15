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

const LoginHeader = () => {
  return (
    <div>
      {!localStorage.getItem("access_token") ? loginButtons() : logoutButton()}
    </div>

    // <div>
    //   {socialType.map((socialObj, idx) => {
    //     return <LoginButton key={idx} social={socialObj} />;
    //   })}
    // </div>
  );
};

export default LoginHeader;
