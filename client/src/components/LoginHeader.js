import LoginButton from "./LoginButton";

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

const LoginHeader = () => {
  return (
    <div>
      {socialType.map((socialObj) => {
        return <LoginButton social={socialObj} />;
      })}
    </div>
  );
};

export default LoginHeader;
