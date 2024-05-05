import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/users';

const createUser = (userData) => {
  return axios.post(`${API_URL}/create`, userData, { headers: authHeader() });
};

const updateUserField = (userId, userData) => {
  return axios.patch(`${API_URL}/update-field/${userId}`, userData, { headers: authHeader() });
};

const updateWholeUser = (userId, userData) => {
  return axios.put(`${API_URL}/whole-update/${userId}`, userData, { headers: authHeader() });
};

const removeUserById = (userId) => {
  return axios.delete(`${API_URL}/remove-user/${userId}`, { headers: authHeader() });
};

const getUsersByBirthdayRange = (from, to) => {
  return axios.get(`${API_URL}/birthday-range`, { params: { from, to }, headers: authHeader() });
};

const UserTools = {
  createUser,
  updateUserField,
  updateWholeUser,
  removeUserById,
  getUsersByBirthdayRange,
};

export default UserTools;
