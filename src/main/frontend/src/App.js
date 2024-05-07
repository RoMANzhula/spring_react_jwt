import React, { useEffect, useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import AuthService from "./services/auth.service";
import eventBus from "./common/EventBus";
import UserContent from "./components/userContent/UserContent";
import ModeratorContent from "./components/moderatorContent/ModeratorContent";
import AdminContent from "./components/adminContent/AdminContent";
import Home from "./components/homePage/Home";
import Login from "./components/login/Login";
import Register from "./components/register/Register";
import "./App.css"
import Profile from "./components/profile/Profile";
import CreateUserPage from "./components/userTools/CreateUserForm";


const App = () => {
  const [showContentModerator, setShowContentModerator] = useState(false);
  const [showContentAdmin, setShowContentAdmin] = useState(false);
  const [currentUser, setCurrentUser] = useState(undefined);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);

      if (user.user.roles && user.user.roles.length > 0) {
        user.user.roles.forEach((role) => {
          if (role.name === "ROLE_MODERATOR") {
            setShowContentModerator(true);
          } else if (role.name === "ROLE_ADMIN") {
            setShowContentModerator(true);
            setShowContentAdmin(true);
          }
        });
      }
    }

    eventBus.on("logout", () => {
      logout();
    });

    return () => {
      eventBus.remove("logout");
    };
  }, []);

  const logout = () => {
    AuthService.logout();
    setShowContentModerator(false);
    setShowContentAdmin(false);
    setCurrentUser(undefined);
  };

  return (
    <div>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <Link to={"/"} className="navbar-brand">
          Great Company!
        </Link>

        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/home"} className="nav-link">
              Home
            </Link>
          </li>

          {showContentModerator && (
            <li className="nav-item">
              <Link to={"/moderator"} className="nav-link">
                Moderator Content
              </Link>
            </li>
          )}

          {showContentAdmin && (
            <li className="nav-item">
              <Link to={"/admin"} className="nav-link">
                Admin Content
              </Link>
            </li>
          )}

          {showContentAdmin && (
            <li className="nav-item">
              <Link to={"/create-user"} className="nav-link">
                Create User
              </Link>
            </li>
          )}

          {currentUser && (
            <li className="nav-item">
              <Link to={"/user"} className="nav-link">
                User content
              </Link>
            </li>
          )}
        </div>

        {currentUser ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {currentUser.user.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={logout}>
                Logout
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Registration
              </Link>
            </li>
          </div>
        )}
      </nav>

      <div className="conttainer mt-3">
        <Routes>
          <Route exact path={"/"} element={<Home />} />
          <Route exact path={"/home"} element={<Home />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/profile" element={<Profile />} />
          <Route path="/user" element={<UserContent />} />
          <Route path="/moderator" element={<ModeratorContent />} />
          <Route path="/admin" element={<AdminContent />} />
          {showContentAdmin && (
            <Route path="/create-user" element={<CreateUserPage />} />
          )}
        </Routes>
      </div>
    </div>
  );
};

export default App;

