import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import songService from "../../services/songService";
import axios from "axios";
import { Dropdown } from "react-bootstrap";

const DetailsSong = ({ isAuthenticated, currentUser }) => {
    const { id } = useParams();
    const [song, setSong] = useState(null);
    const [liked, setLiked] = useState(false);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchSongData = async () => {
            try {
                // Fetch song details
                const songData = await songService.getSongById(id);
                setSong(songData);

                // Check if user is authenticated before fetching liked status
                if (!isAuthenticated || !currentUser) {
                    setLoading(false);
                    return;
                }

                // Fetch liked songs for the user
                const response = await axios.get(
                    `https://music-application-sb.onrender.com/api/users/${currentUser.username}/liked-songs`,
                    { withCredentials: true }
                );

                // Check if this song is in the liked songs list
                const isLiked = response.data.some((likedSong) => likedSong.id === songData.id);
                setLiked(isLiked);
            } catch (error) {
                console.error("Error fetching song details:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchSongData();
    }, [id, isAuthenticated, currentUser]);

    // Handle song delete
    const handleDelete = async () => {
        if (window.confirm("Are you sure you want to delete this song?")) {
            try {
                await songService.deleteSong(id);
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
        return <div>Loading...</div>;
    }

    if (!song) {
        return <div>Error: Song not found</div>;
    }

    return (
        <div className="container-sm mt-5 p-4" style={{ maxWidth: "900px" }}>
            <h1 className="text-center">{song.title}</h1>
            <h6 className="text-center">by {song.artist.name}</h6>

            <div className="d-flex justify-content-center">
                <img
                    className="img-fluid"
                    src={song.album.imageUrl}
                    style={{ width: "200px", height: "200px", objectFit: "cover" }}
                    alt={song.title}
                />
            </div>

            <div className="d-flex justify-content-center p-3">
                <Dropdown>
                    <Dropdown.Toggle variant="primary" id="dropdown-options">
                        Options
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        {/* Like / Unlike */}
                        {liked ? (
                            <Dropdown.Item onClick={handleUnlike}>Dislike</Dropdown.Item>
                        ) : (
                            <Dropdown.Item onClick={handleLike}>Like</Dropdown.Item>
                        )}

                        <Dropdown.Divider />

                        {/* Edit & Delete (if admin) */}
                        <Dropdown.Item as={Link} to={`/songs/edit/${song.id}`}>Edit</Dropdown.Item>
                        <Dropdown.Item onClick={handleDelete}>Delete</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            </div>

            <div className="mt-4 d-flex justify-content-center">
                <div className="card shadow-sm p-3" style={{ width: "80%" }}>
                    <h3 className="text-center">Album: {song.album.title}</h3>
                    <Link to={`/albums/${song.album.id}`} className="list-group-item">
                        <div className="d-flex align-items-center">
                            <img
                                src={song.album.imageUrl}
                                alt={song.album.title}
                                className="rounded me-3"
                                style={{ width: "50px", height: "50px", objectFit: "cover" }}
                            />
                            <h5 className="mb-0">{song.album.title}</h5>
                        </div>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default DetailsSong;
