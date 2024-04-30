import axios from "axios";


const API_URL = "http://localhost:8080/api/auth";

const register = (email, username, password, phone) => {
  return axios.post(API_URL + "/registration", {
    email,
    username,
    password,
    phone,
  });
};

const login = (username, password) => {
  return axios.post(API_URL + "/login", {
    username,
    password,
  })
  .then((response) => {
    if (response.data.username) {
      localStorage.setItem("userToken", JSON.stringify(response.data.token));
    }

    return response.data;
  });
};

const logout = () => {
  localStorage.removeItem("userToken");

  return axios.post(API_URL + "/logout").then((response) => {
    return response.data;
  });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("userToken"));
};

const AuthService = {
  register,
  login,
  login,
  getCurrentUser,
}

export default AuthService;