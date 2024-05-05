import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/content';

  const getPublicContent = () => {
    return axios.get(API_URL + '/public');
  };

  const getUserContent = () => {
    return axios.get(API_URL + '/user', { headers: authHeader() });
  };

  const getModeratorContent = () => {
    return axios.get(API_URL + '/moderator', { headers: authHeader() });
  };

  const getAdminContent = () => {
    return axios.get(API_URL + '/admin', { headers: authHeader() });
  };

  const StaffService = {
    getPublicContent,
    getUserContent,
    getModeratorContent,
    getAdminContent,
  };
  
  export default StaffService;
  