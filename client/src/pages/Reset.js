import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Reset = () => {
  const navigator = useNavigate();
  useEffect(() => {
    navigator("/", { replace: true });
  }, []);
};

export default Reset;
