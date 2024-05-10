import { useState, useEffect } from "react";
import UserTools from "../../services/user-tools.service";
import UpdateUserForm from "./UpdateUserForm";

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState(null);
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setLoading(true);
        const response = await UserTools.getAllUsers();
        const sortedUsers = response.data.sort((a, b) => a.firstName.localeCompare(b.firstName));
        setUsers(sortedUsers);
      } catch (error) {
        console.error("Error fetching users:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  const handleUserClick = (userId) => {
    setSelectedUserId(userId);
  };

  const handleFilterSubmit = async () => {
    try {
      setLoading(true);
      const response = await UserTools.getUsersByBirthdayRange(fromDate, toDate);
      const sortedUsers = response.data.sort((a, b) => a.firstName.localeCompare(b.firstName));
      setUsers(sortedUsers);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleFilterReset = () => {
    setFromDate("");
    setToDate("");
    setLoading(true);
    UserTools.getAllUsers()
      .then(response => {
        const sortedUsers = response.data.sort((a, b) => a.firstName.localeCompare(b.firstName));
        setUsers(sortedUsers);
      })
      .catch(error => console.error("Error fetching users:", error))
      .finally(() => setLoading(false));
  };

  return (
    <div>
      <h3>User List</h3>
      <div>
        <label htmlFor="fromDate">From:</label>
        <input type="date" id="fromDate" name="fromDate" value={fromDate} onChange={(e) => setFromDate(e.target.value)} />
        <label htmlFor="toDate">To:</label>
        <input type="date" id="toDate" name="toDate" value={toDate} onChange={(e) => setToDate(e.target.value)} />
        <button onClick={handleFilterSubmit}>Filter</button>
        <button onClick={handleFilterReset}>Reset</button>
      </div>
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

