import React, { useEffect, useState } from "react"
import StaffService from "../../services/staff-content.service";


const Home = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    StaffService.getPublicContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const contentValue =
          (error.response &&
          error.response.data) ||
          error.message ||
          error.toString();

        setContent(contentValue);
      }
    );
  }, []);

  return (
    <div className="container">
      <header className="jumbotron">
        <h2>{content}</h2>
      </header>
    </div>
  );
};

export default Home;
