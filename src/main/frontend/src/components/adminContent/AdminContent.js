import { useEffect, useState } from "react"
import UserService from "../../services/staff-content.service";
import eventBus from "../../common/EventBus";


const AdminContent = () => {
  const [content, setContent] = useState("");

  useEffect(() => {
    UserService.getAdminContent().then(
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
      <header className="container">
        <h2>{content}</h2>
      </header>
    </div>
  );
};

export default AdminContent;

