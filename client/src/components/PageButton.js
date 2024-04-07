const PageButton = ({ type, pageNum, onClick }) => {
  const btnType = ["default", "positive"].includes(type) ? type : "default";

  return (
    <button
      className={["PageButton", `PageButton${btnType}`].join(" ")}
      onClick={() => {
        console.log("진짜 눌림 넘버" + pageNum);
        onClick(pageNum);
      }}
    >
      {pageNum}
    </button>
  );
};

PageButton.defaultProps = {
  type: "default",
};

export default PageButton;
