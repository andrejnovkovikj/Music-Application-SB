import { Navigate, Outlet } from 'react-router-dom';

function PrivateRoute({ isAuthenticated }) {
    if (!isAuthenticated) {
        // If not authenticated, redirect to the login page
        return <Navigate to="/login" />;
    }

    return <Outlet />; // Allow access to the protected route if authenticated
}

export default PrivateRoute;
