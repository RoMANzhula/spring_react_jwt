import { useEffect, useState } from "react"
import UserService from "../../services/staff-content.service";
import eventBus from "../../common/EventBus";


const ModeratorContent = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getModeratorContent().then(
      (response) => {
        setContent(response.data);
      },
      (error) => {
        const contentValue = 
        (error.response &&
        error.response.data &&
        error.response.data.message) ||
        error.message ||
        error.toString();

        setContent(contentValue);

        if (error.response && error.response.status === 401) {
          eventBus.dispatch("logout");
        }
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

export default ModeratorContent;

