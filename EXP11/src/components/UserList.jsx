import React, { useState, useEffect } from 'react';

const UserList = () => {
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchUsers = () => {
        setLoading(true);
        fetch('https://jsonplaceholder.typicode.com/users')
            .then(res => {
                if (!res.ok) throw new Error('Network response was not ok');
                return res.json();
            })
            .then(data => {
                setUsers(data);
                setLoading(false);
            })
            .catch(err => {
                setError(err.message);
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    if (loading) return <div className="loading">Loading API users...</div>;
    if (error) return <div className="error">Error: {error}</div>;

    return (
        <div className="list-container">
            <h2>User List (From External API)</h2>
            <div className="card-grid">
                {users.map(user => (
                    <div className="card" key={user.id}>
                        <h3>{user.name}</h3>
                        <p><strong>Email:</strong> {user.email}</p>
                        <p><strong>Phone:</strong> {user.phone}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default UserList;
