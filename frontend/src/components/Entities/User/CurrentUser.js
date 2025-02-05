import React, { useState, useEffect } from 'react';

const CurrentUser = () => {
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetch current user data from backend without authentication token
        fetch("https://music-application-sb.onrender.com/api/users/current-user", {
            method: "GET",
            withCredentials: true // Важно за session-based authentication
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }
                return response.json();
            })
            .then(data => {
                setCurrentUser(data);
                setLoading(false);
            })
            .catch(err => {
                setError(err.message);
                setLoading(false);
            });
    }, []); // Empty dependency array so it runs only once after initial render

    if (loading) {
        return <div>Loading user data...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <h3>Welcome, {currentUser.username}</h3>
            <p>Email: {currentUser.email}</p>
            <p>Role: {currentUser.role}</p>
            <p>Premium Status: {currentUser.isPremium ? 'Yes' : 'No'}</p>
            {/* You can display more user details as needed */}
        </div>
    );
};

export default CurrentUser;
