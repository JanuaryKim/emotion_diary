import { useState, useContext, useEffect } from "react";
import MyHeader from "../components/MyHeader";
import MyButton from "../components/MyButton";
import { DiaryStateContext } from "../App";
import DiaryList from "../components/DiaryList";
import LoginHeader from "../components/LoginHeader";

const Home = () => {
  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `감정 일기장`;
  }, []);

  const [data, setData] = useState([]);
  const [curDate, setCurDate] = useState(new Date());
  const headText = `${curDate.getFullYear()}년 ${curDate.getMonth() + 1}월`;
  const diaryList = useContext(DiaryStateContext);

  useEffect(() => {
    if (diaryList.length < 1) {
      return;
    }

    const firstDay = new Date(
      curDate.getFullYear(),
      curDate.getMonth(),
      2,
      -15
    ); //이번달의 첫날 0시(시의 기본값이 15이므로 -15)

    const lastDay = new Date(
      curDate.getFullYear(),
      curDate.getMonth() + 1,
      1,
      8,
      59,
      59
    ); //이번달의 마지막날 23시 59분 59초 (시의 기본값이 15이므로 + 8)

    // const testDay = new Date(2021, 2, 2); //2021년 3월 1일

    const newData = diaryList.filter((it) => {
      return firstDay.getTime() <= it.date && it.date <= lastDay.getTime();
    });

    setData(newData);
  }, [diaryList, curDate]); //diaryList 이유 : 일기가 추가, 삭제 됬을때도 자동으로 update 시키기 위해, curDate 이유 : 버튼으로 다음 혹은 전달로 이동시에도 다시 update 시키기 위해

  const increaseMonth = () => {
    setCurDate(
      new Date(curDate.getFullYear(), curDate.getMonth() + 1, curDate.getDate()) //생각해보면 신기한 구조임. new Date의 두번째 인자(월)로 12를 넣으면 첫번째 인자의 + 1을 시킨 년도와 1월 그리고 일을 기준으로 Date 객체를 만듦. 그러므로 Month + 1 를 하는것 자체만으로 자동으로 년도까지 증가 됨
    );
  };

  const decreaseMonth = () => {
    setCurDate(
      new Date(curDate.getFullYear(), curDate.getMonth() - 1, curDate.getDate()) //생각해보면 신기한 구조임. new Date의 두번째 인자(월)로 12를 넣으면 첫번째 인자의 + 1을 시킨 년도와 1월 그리고 일을 기준으로 Date 객체를 만듦. 그러므로 Month + 1 를 하는것 자체만으로 자동으로 년도까지 증가 됨
    );
  };
  return (
    <div>
      <LoginHeader />
      <MyHeader
        headText={headText}
        leftChild={<MyButton text={"<"} type={""} onClick={decreaseMonth} />}
        rightChild={<MyButton text={">"} type={""} onClick={increaseMonth} />}
      />
      <DiaryList diaryList={data} />
    </div>
  );
};

export default Home;
