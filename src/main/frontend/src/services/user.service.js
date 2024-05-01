import axios from "axios";

const API_URL = "http://localhost:8080/api/content"

const getPublicContent = () => {
  return axios.get(API_URL + "/public");
};

const getUserContent = () => {
  return axios.get(API_URL + "/user");
};

const getModeratorContent = () => {
  return axios.get(API_URL + "/moderator");
};

const getAdminContent = () => {
  return axios.get(API_URL + "/admin");
};

const UserService = {
  getPublicContent,
  getUserContent,
  getModeratorContent,
  getAdminContent,
}

export default UserService;
