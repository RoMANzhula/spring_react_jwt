import { useState, useEffect } from "react";
import UserTools from "../../services/user-tools.service";
import UpdateUserForm from "./UpdateUserForm";

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await UserTools.getAllUsers();
        setUsers(response.data);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    fetchUsers();
  }, []);

  const handleUserClick = (userId) => {
    setSelectedUserId(userId);
  };

  return (
    <div>
      <h3>User List</h3>
      <ul>
        {users.map((user) => (
          <li key={user.id} onClick={() => handleUserClick(user.id)}>
            {user.firstName} {user.lastName}
          </li>
        ))}
      </ul>
      {selectedUserId && <UpdateUserForm userId={selectedUserId} />}
    </div>
  );
};

export default UserList;
