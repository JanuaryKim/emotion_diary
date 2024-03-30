import DiaryEditor from "../components/DiaryEditor";
import { useEffect } from "react";
const New = () => {
  useEffect(() => {
    const titleElements = document.getElementsByTagName("title")[0];
    titleElements.innerHTML = `일기 작성`;
  }, []);

  return (
    <div>
      <DiaryEditor />
    </div>
  );
};

export default New;
