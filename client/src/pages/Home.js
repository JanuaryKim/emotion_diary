import { getDiaryPageData } from "../apis/getDiaryPageData";
import { useState, useContext, useEffect } from "react";
import MyHeader from "../components/MyHeader";
import MyButton from "../components/MyButton";
import App, { DiaryStateContext } from "../App";
import DiaryList from "../components/DiaryList";
import LoginHeader from "../components/LoginHeader";
import PageNumber from "../components/PageNumber";

const Home = () => {
  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `감정 일기장`;
  }, []);

  const [data, setData] = useState([]);
  const [curDate, setCurDate] = useState(new Date());
  const headText = `${curDate.getFullYear()}년 ${curDate.getMonth() + 1}월`;
  const { diaryList, dataId } = useContext(DiaryStateContext);
  const [curPage, setCurPage] = useState(1);
  const [totalPage, setTotalPage] = useState(1);

  useEffect(() => {
    if (localStorage.getItem("access_token")) {
      //테스트
      const diaryArr = async () => {
        console.log("api요청");
        const formattedDate = `${curDate.getFullYear()}-${String(
          curDate.getMonth() + 1
        ).padStart(2, "0")}`;
        const pageSize = process.env.REACT_APP_PAGE_SIZE;
        const url = `page=${curPage}&size=${pageSize}&date=${formattedDate}`;
        console.log("url : " + url);

        const diaryPageData = await getDiaryPageData(url);

        //총 페이지수 계산
        const totalPCnt =
          diaryPageData.diaryTotalCount == 0
            ? 1
            : diaryPageData.diaryTotalCount % pageSize == 0
            ? diaryPageData.diaryTotalCount / pageSize
            : Math.floor(diaryPageData.diaryTotalCount / pageSize + 1);

        //서버에서 받은 데이터 가공
        const mapData = diaryPageData.diaryList.map((d) => {
          return {
            id: dataId.current++,
            date: new Date(d.regDate).getTime(),
            content: d.content,
            emotion: d.emotion,
          };
        });

        //현재 페이지도 어떻게 세팅을 해야 함
        setTotalPage(totalPCnt);
        setData(mapData);
      };
      diaryArr();
    } else {
      //서버 요청 없는 diaryList는 날짜별로 데이터를 가지고 있는게 아니라 모든 날짜의 데이터를 다 가지고 있다. 그러므로 아예 없다면 절대 그릴일이 없고,
      //하나라도 있다면 아래 로직에서 필터링 되어 빈 배열이라도 set이 되기 때문에, 날짜별로 일기리스트들이 다시 그려질 수 있음
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

      console.log(diaryList);
      const newData = diaryList.filter((it) => {
        return firstDay.getTime() <= it.date && it.date <= lastDay.getTime();
      });

      setData(newData);
    }
  }, [diaryList, curDate, curPage]); //diaryList 이유 : 일기가 추가, 삭제 됬을때도 자동으로 update 시키기 위해,

  // useEffect(() => {
  // //서버 요청 없는 diaryList는 날짜별로 데이터를 가지고 있는게 아니라 모든 날짜의 데이터를 다 가지고 있다. 그러므로 아예 없다면 절대 그릴일이 없고,
  // //하나라도 있다면 아래 로직에서 필터링 되어 빈 배열이라도 set이 되기 때문에, 날짜별로 일기리스트들이 다시 그려질 수 있음
  //   if (diaryList.length < 1) {
  //     return;
  //   }

  //   const firstDay = new Date(
  //     curDate.getFullYear(),
  //     curDate.getMonth(),
  //     2,
  //     -15
  //   ); //이번달의 첫날 0시(시의 기본값이 15이므로 -15)

  //   const lastDay = new Date(
  //     curDate.getFullYear(),
  //     curDate.getMonth() + 1,
  //     1,
  //     8,
  //     59,
  //     59
  //   ); //이번달의 마지막날 23시 59분 59초 (시의 기본값이 15이므로 + 8)

  //   // const testDay = new Date(2021, 2, 2); //2021년 3월 1일

  //   console.log(diaryList);
  //   const newData = diaryList.filter((it) => {
  //     return firstDay.getTime() <= it.date && it.date <= lastDay.getTime();
  //   });

  //   setData(newData);
  // }, [diaryList, curDate]); //diaryList 이유 : 일기가 추가, 삭제 됬을때도 자동으로 update 시키기 위해, curDate 이유 : 버튼으로 다음 혹은 전달로 이동시에도 다시 update 시키기 위해

  const increaseMonth = () => {
    setCurDate(
      new Date(curDate.getFullYear(), curDate.getMonth() + 1, curDate.getDate()) //생각해보면 신기한 구조임. new Date의 두번째 인자(월)로 12를 넣으면 첫번째 인자의 + 1을 시킨 년도와 1월 그리고 일을 기준으로 Date 객체를 만듦. 그러므로 Month + 1 를 하는것 자체만으로 자동으로 년도까지 증가 됨
    );
    setCurPage(1); //날짜가 바뀌면 현재 페이지도 1로 초기화
  };

  const decreaseMonth = () => {
    setCurDate(
      new Date(curDate.getFullYear(), curDate.getMonth() - 1, curDate.getDate()) //생각해보면 신기한 구조임. new Date의 두번째 인자(월)로 12를 넣으면 첫번째 인자의 + 1을 시킨 년도와 1월 그리고 일을 기준으로 Date 객체를 만듦. 그러므로 Month + 1 를 하는것 자체만으로 자동으로 년도까지 증가 됨
    );
    setCurPage(1); //날짜가 바뀌면 현재 페이지도 1로 초기화
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
      <PageNumber
        currentPage={curPage}
        totalPageCount={totalPage}
        onClick={setCurPage}
      />
    </div>
  );
};

export default Home;
