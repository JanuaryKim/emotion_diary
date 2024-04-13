import PageButton from "./PageButton";

const renderPages = (currentPage, totalPageCount, onClick) => {
  let idx = 0;
  const elements = [];

  //이전 페이지
  if (currentPage - 4 === 1 || currentPage - 4 < 1) {
    for (let i = 1; i < currentPage; i++) {
      elements.push(<PageButton key={idx++} pageNum={i} onClick={onClick} />);
    }
  } else {
    elements.push(<PageButton key={idx++} pageNum={1} onClick={onClick} />);
    elements.push(<span> ... </span>);
    for (let i = currentPage - 3; i < currentPage; i++) {
      elements.push(<PageButton key={idx++} pageNum={i} onClick={onClick} />);
    }
  }

  //현재 페이지
  elements.push(
    <PageButton
      key={idx++}
      pageNum={currentPage}
      type={"positive"}
      onClick={onClick}
    />
  );

  //이후 페이지
  if (currentPage + 4 === totalPageCount || currentPage + 4 > totalPageCount) {
    for (let i = currentPage + 1; i <= totalPageCount; i++) {
      elements.push(<PageButton key={idx++} pageNum={i} onClick={onClick} />);
    }
  } else {
    for (let i = currentPage + 1; i <= currentPage + 3; i++) {
      elements.push(<PageButton key={idx++} pageNum={i} onClick={onClick} />);
    }
    elements.push(<span> ... </span>);
    elements.push(
      <PageButton key={idx++} pageNum={totalPageCount} onClick={onClick} />
    );
  }

  return elements;
};

//하위 페이지 넘버 출력하는 컴포넌트
const PageNumber = ({ currentPage, totalPageCount, onClick }) => {
  return <div>{renderPages(currentPage, totalPageCount, onClick)}</div>;
};

export default PageNumber;
