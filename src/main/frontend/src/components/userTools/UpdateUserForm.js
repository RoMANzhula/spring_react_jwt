import React, { useState, useEffect } from "react";
import { format } from 'date-fns';
import UserTools from "../../services/user-tools.service";
import { useNavigate } from "react-router-dom";


const UpdateUserForm = ({ userId }) => {
  const navigate = useNavigate();

  const todayDate = new Date();
  const formattedDate = format(todayDate, 'yyyy-MM-dd');

  const [userData, setUserData] = useState({
    email: "",
    firstName: "",
    lastName: "",
    birthDate: formattedDate,
    address: "",
    phoneNumber: "",
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [errors, setErrors] = useState({
    email: "",
    firstName: "",
    lastName: "",
    birthDate: "",
    address: "",
    phoneNumber: "",
  });

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await UserTools.getUser(userId);
        const user = response.data;
        setUserData({
          email: user.email || "",
          firstName: user.firstName || "",
          lastName: user.lastName || "",
          birthDate: user.birthDate || formattedDate,
          address: user.address || "",
          phoneNumber: user.phoneNumber || "",
        });
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };

    fetchUserData();

    // Clean up function to clear userData when unmounting component
    return () => setUserData({
      email: "",
      firstName: "",
      lastName: "",
      birthDate: formattedDate,
      address: "",
      phoneNumber: "",
    });
  }, [userId, formattedDate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    
    setErrors((prevErrors) => ({
      ...prevErrors,
      [name]: value ? "" : `${name} is required`,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");
    setLoading(true);

    const formValid = Object.values(userData).every((value) => !!value);
    if (!formValid) {
        setErrors((prevErrors) => ({
            ...prevErrors,
            ...Object.fromEntries(
                Object.entries(userData)
                    .filter(([key, value]) => !value)
                    .map(([key]) => [key, `${key} is required`])
            ),
        }));
        setLoading(false);
        return;
    }

    try {
        await UserTools.updateUserField(userId, userData);
        alert("User successfully updated!");
        setUserData({
            email: "",
            firstName: "",
            lastName: "",
            birthDate: formattedDate,
            address: "",
            phoneNumber: "",
        });
        navigate("/all-users");
        window.location.reload();
    } catch (error) {
        if (error.response && error.response.data) {
            const errorMessage = error.response.data;
            setMessage(errorMessage);
        } else {
            setMessage("An error occurred while processing your request.");
        }
    } finally {
        setLoading(false);
    }
};

const handleDeleteUser = async () => {
  try {
    await UserTools.removeUserById(userId);
    alert("User successfully deleted!");
    navigate("/all-users");
    window.location.reload();
  } catch (error) {
    console.error("Error deleting user:", error);
  }
};

  return (
    <div>
      <h3>Update User</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="email"
          name="email"
          value={userData.email}
          onChange={handleChange}
          placeholder="Email"
          required
        />
        {errors.email && <div className="error">{errors.email}</div>}
        <input
          type="text"
          name="firstName"
          value={userData.firstName}
          onChange={handleChange}
          placeholder="Firstname"
          required
        />
        {errors.firstName && <div className="error">{errors.firstName}</div>}
        <input
          type="text"
          name="lastName"
          value={userData.lastName}
          onChange={handleChange}
          placeholder="Lastname"
          required
        />
        {errors.lastName && <div className="error">{errors.lastName}</div>}
        <input
          type="date"
          name="birthDate"
          value={format(new Date(userData.birthDate), 'yyyy-MM-dd')}
          onChange={handleChange}
          placeholder="Birthdate"
          required
        />
        {errors.birthDate && <div className="error">{errors.birthDate}</div>}
        <input
          type="text"
          name="address"
          value={userData.address}
          onChange={handleChange}
          placeholder="Address"
          required
        />
        {errors.address && <div className="error">{errors.address}</div>}
        <input
          type="tel"
          name="phoneNumber"
          value={userData.phoneNumber}
          onChange={handleChange}
          placeholder="Phone number"
          required
        />
        {errors.phoneNumber && (
          <div className="error">{errors.phoneNumber}</div>
        )}
        <button type="submit" disabled={loading}>
          {loading && <span className="spinner-border spinner-border-sm"></span>}
          <span>Update</span>
        </button>
        <button type="button" className="ml-3" onClick={handleDeleteUser}>Delete User</button>
      </form>
      {message && <div className="error">{message}</div>}
    </div>
  );
};

export default UpdateUserForm;

