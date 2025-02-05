import React, { useEffect, useState } from "react";

const CurrentUser = () => {
    const [currentUser, setCurrentUser] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetch current user data
        fetch("https://music-application-sb.onrender.com/api/users/current-user", {
            method: "GET",
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch current user data");
                }
                return response.json();
            })
            .then(data => {
                setCurrentUser(data);
            })
            .catch(error => {
                console.error("Error fetching current user data: ", error);
                setError(error.message);
            });
    }, []);

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!currentUser) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container-sm mt-5 p-4" style={{ maxWidth: "900px" }}>
            <h1 className="text-center">Current User</h1>
            <pre>{JSON.stringify(currentUser, null, 2)}</pre>
        </div>
    );
};

export default CurrentUser;
