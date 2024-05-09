import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthService from "../../services/auth.service";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");

  const navigate = useNavigate();

  const onChangeUsername = (e) => {
    const value = e.target.value;
    setUsername(value);
    setUsernameError(value ? "" : "Username is required");
  };

  const onChangePassword = (e) => {
    const value = e.target.value;
    setPassword(value);
    setPasswordError(value ? "" : "Password is required");
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    setMessage("");
    setLoading(true);

    if (!username || !password) {
      setUsernameError(username ? "" : "Username is required");
      setPasswordError(password ? "" : "Password is required");
      setLoading(false);
      return;
    }

    try {
      await AuthService.login(username, password);
      navigate("/profile");
      window.location.reload();
    } catch (error) {
      const resMessage =
        (error.response &&
          error.response.data &&
          error.response.data.message) ||
        error.message ||
        error.toString();

      setLoading(false);
      setMessage(resMessage);
    }
  };

  return (
    <div className="col-md-12">
      <div className="card card-container">
        <img
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          alt="profile-img"
          className="profile-img-card"
        />

        <form onSubmit={handleLogin}>
          <div className="form-group">
            <label htmlFor="username"  style={{ color: 'blue' }} >Username</label>
            <input
              type="text"
              className="form-control"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
            {usernameError && (
              <div className="invalid-feedback d-block">{usernameError}</div>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password" style={{ color: 'blue' }} >Password</label>
            <input
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
            {passwordError && (
              <div className="invalid-feedback d-block">{passwordError}</div>
            )}
          </div>

          <div className="form-group mt-3 text-center">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Login</span>
            </button>
          </div>

          {message && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
        </form>
      </div>
    </div>
  );
};

export default Login;

