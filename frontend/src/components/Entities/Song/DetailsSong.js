import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import songService from "../../services/songService";
import axios from "axios";
import { Dropdown, Spinner, Alert } from "react-bootstrap";

const DetailsSong = ({ isAuthenticated, currentUser }) => {
    const { id } = useParams();
    const [song, setSong] = useState(null);
    const [liked, setLiked] = useState(false);
    const [isAdmin, setIsAdmin] = useState(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSong = async () => {
            try {
                // Fetch song details
                const songData = await songService.getSongById(id);
                setSong(songData);

                if (!isAuthenticated || !currentUser) {
                    setLoading(false);
                    return;
                }

                // Fetch liked songs for user
                const response = await axios.get(
                    `https://music-application-sb.onrender.com/api/users/${currentUser.username}/liked-songs`,
                    { withCredentials: true }
                );
                setLiked(response.data.some((song) => song.id === songData.id));

                // Check if user is admin
                const adminResponse = await axios.get(
                    `https://music-application-sb.onrender.com/api/users/current-user-role/${currentUser.username}`,
                    { withCredentials: true }
                );
                setIsAdmin(adminResponse.data);
            } catch (error) {
                console.error("Error fetching song:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchSong();
    }, [id, isAuthenticated, currentUser]);

    const handleDelete = async (songId) => {
        if (window.confirm("Are you sure you want to delete this song?")) {
            try {
                await axios.delete(`https://music-application-sb.onrender.com/api/songs/delete${songId}`);
                navigate("/songs");
            } catch (error) {
                console.error("Error deleting song:", error);
            }
        }
    };

    // Handle like action
    const handleLike = async () => {
        try {
            await axios.post(
                `https://music-application-sb.onrender.com/api/songs/${id}/like`,
                {},
                { withCredentials: true }
            );
            setLiked(true);
        } catch (error) {
            console.error("Error liking the song", error);
        }
    };

    // Handle unlike action
    const handleUnlike = async () => {
        try {
            await axios.post(
                `https://music-application-sb.onrender.com/api/songs/${id}/unlike`,
                {},
                { withCredentials: true }
            );
            setLiked(false);
        } catch (error) {
            console.error("Error unliking the song", error);
        }
    };

    if (loading) {
        return (
            <div className="text-center mt-5">
                <Spinner animation="border" role="status" />
                <p>Loading song details...</p>
            </div>
        );
    }

    if (!song) {
        return <Alert variant="danger" className="text-center mt-5">Error: Song not found</Alert>;
    }

    return (
        <div className="container-sm mt-5 p-4" style={{ maxWidth: "900px" }}>
            <h1 className="text-center">{song.title}</h1>
            <h6 className="text-center">{song.album?.title}</h6>

            <div className="d-flex justify-content-center">
                <img
                    className="img-fluid"
                    src={song.album.imageUrl}
                    alt={song.title}
                    style={{ width: "200px", height: "200px", objectFit: "cover" }}
                />
            </div>

            <p className="text-center">Length: {song.lengthSeconds} seconds</p>
            <p className="text-center">Artist: {song.artist?.name}</p>

            <div className="d-flex justify-content-center p-3">
                <Dropdown>
                    <Dropdown.Toggle variant="primary" id="dropdown-options">
                        Options
                    </Dropdown.Toggle>

                    <Dropdown.Menu>
                        {/* Admin Options */}
                        {isAdmin && (
                            <>
                                <Dropdown.Item as={Link} to={`/songs/edit/${song.id}`}>Edit</Dropdown.Item>
                                <Dropdown.Item onClick={() => handleDelete(song.id)}>Delete</Dropdown.Item>
                                <Dropdown.Divider />
                            </>
                        )}

                        {/* Like / Unlike */}
                        {liked ? (
                            <Dropdown.Item onClick={handleUnlike}>Dislike</Dropdown.Item>
                        ) : (
                            <Dropdown.Item onClick={handleLike}>Like</Dropdown.Item>
                        )}
                    </Dropdown.Menu>
                </Dropdown>
            </div>
        </div>
    );
};

export default DetailsSong;
