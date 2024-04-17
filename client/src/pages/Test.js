import { useEffect } from "react";
import "react-image-gallery/styles/css/image-gallery.css";
import { useNavigate } from "react-router-dom";

const Test = () => {
  const imageData = localStorage.getItem("image");
  if (imageData) {
    const image = new Image();
    image.src = imageData;
    document.body.appendChild(image);
  }

  return <div></div>;
};
export default Test;
