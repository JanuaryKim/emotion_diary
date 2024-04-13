const PageButton = ({ type, pageNum, onClick }) => {
  const btnType = ["default", "positive"].includes(type) ? type : "default";

  return (
    <button
      className={["PageButton", `PageButton${btnType}`].join(" ")}
      onClick={() => {
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
