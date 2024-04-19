import { getDiaryPageData } from "../apis/getDiaryPageData";
import { useState, useContext, useEffect } from "react";
import MyHeader from "../components/MyHeader";
import MyButton from "../components/MyButton";
import App, { DiaryStateContext } from "../App";
import DiaryList from "../components/DiaryList";
import LoginHeader from "../components/LoginHeader";
import PageNumber from "../components/PageNumber";
import { getTotalPageCnt } from "../util/page";
import { getMappingDiaryListFromServer } from "../util/mapping";

const Home = () => {
  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `감정 일기장`;
  }, []);

  const [curDate, setCurDate] = useState(new Date());
  const headText = `${curDate.getFullYear()}년 ${curDate.getMonth() + 1}월`;
  const [data, setData] = useState([]);
  const [curPage, setCurPage] = useState(1);
  const [totalPage, setTotalPage] = useState(1);
  const [sortType, setSortType] = useState("latest");
  const [filter, setFilter] = useState("all");
  const { login, localData } = useContext(DiaryStateContext);

  const getPage = async (page) => {
    const formattedDate = `${curDate.getFullYear()}-${String(
      curDate.getMonth() + 1
    ).padStart(2, "0")}`;
    const pageSize = process.env.REACT_APP_PAGE_SIZE;
    const url = `page=${page}&size=${pageSize}&date=${formattedDate}&sort=${sortType}&emotion=${filter}`;
    const diaryPageData = await getDiaryPageData(url);

    const totalPageCount = getTotalPageCnt(
      diaryPageData.diaryTotalCount,
      pageSize
    );
    //서버에서 받은 데이터 가공
    const diaryList = getMappingDiaryListFromServer(diaryPageData.diaryList);
    setTotalPage(totalPageCount);
    setData(diaryList);
  };

  const datefiltering = (diaryList) => {
    const firstDay = new Date(curDate.getFullYear(), curDate.getMonth(), 1, 0); //이번달의 첫날 0시(시의 기본값이 15이므로 -15)
    const lastDay = new Date(
      curDate.getFullYear(),
      curDate.getMonth() + 1,
      0,
      23,
      59,
      59
    );
    return diaryList.filter(
      (it) => firstDay.getTime() <= it.date && it.date <= lastDay.getTime()
    );
  };

  const emotionFiltering = (diaryList) => {
    const emotionFilter = (it) => {
      if (filter === "good") {
        return it.emotion <= 3;
      } else {
        return it.emotion > 3;
      }
    };
    return filter === "all" ? diaryList : diaryList.filter(emotionFilter);
  };

  const sorting = (diaryList) => {
    const compare = (d1, d2) => {
      if (sortType === "latest") {
        return parseInt(d2.date) - parseInt(d1.date); //문자열로 들어올지도 몰라서 parseInt
      } else {
        return parseInt(d1.date) - parseInt(d2.date);
      }
    };
    return diaryList.sort(compare);
  };

  const getPageFromLocal = async (page) => {
    const dateFilteredData = datefiltering(localData);
    const emotionFilteredData = emotionFiltering(dateFilteredData);
    const sortedData = sorting(emotionFilteredData);

    const totalDiaryCnt = sortedData.length;
    const startIdx = (page - 1) * process.env.REACT_APP_PAGE_SIZE;
    const endIdx =
      startIdx + process.env.REACT_APP_PAGE_SIZE > sortedData.length
        ? sortedData.length
        : startIdx + process.env.REACT_APP_PAGE_SIZE;

    setTotalPage(totalDiaryCnt / process.env.REACT_APP_PAGE_SIZE + 1);
    const finishData = sortedData.slice(startIdx, endIdx);

    setData(finishData);
  };

  useEffect(() => {
    if (login) {
      getPage(1);
    } else {
      getPageFromLocal(1);
    }
  }, [login, sortType, filter, curDate, localData]);

  const onClickPageButton = (page) => {
    //페이지 버튼 눌렀을 때

    if (login) {
      getPage(page);
    } else {
      getPageFromLocal(page);
    }
  };

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
      <DiaryList
        diaryList={data}
        sortType={sortType}
        setSortType={setSortType}
        filter={filter}
        setFilter={setFilter}
      />
      <PageNumber
        currentPage={curPage}
        totalPageCount={totalPage}
        onClick={onClickPageButton}
      />
    </div>
  );
};

export default Home;
