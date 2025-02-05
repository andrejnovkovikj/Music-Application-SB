import { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";

const AuthContext = createContext();

export const useAuth = () => {
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const checkLoginStatus = async () => {
        try {
            const response = await axios.get("https://music-application-sb.onrender.com/api/users/current-user", {
                method: "GET",
                credentials: "include", // Ensure cookies are included
            });

            if (response.ok) {
                const userData = await response.json();
                setUser(userData); // Set the logged-in user
            } else {
                setUser(null); // No user logged in
            }
        } catch (error) {
            console.error("Error checking login status:", error);
            setUser(null); // In case of error, assume no user logged in
        }
    };

    useEffect(() => {
        checkLoginStatus(); // Check login status when the app loads
    }, []);

    return (
        <AuthContext.Provider value={{ user, setUser, checkLoginStatus }}>
            {children}
        </AuthContext.Provider>
    );
};
