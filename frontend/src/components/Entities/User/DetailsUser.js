import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import userService from "../../services/userService";
import songService from "../../services/songService";
import albumService from "../../services/albumService";
import playlistService from "../../services/playlistService";

const DetailsUser = () => {
    const { id } = useParams();
    const [user, setUser] = useState(null);
    const [likedSongs, setLikedSongs] = useState([]);
    const [likedAlbums, setLikedAlbums] = useState([]);
    const [playlists, setPlaylists] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                console.log(`Fetching user data for ID: ${id}`);

                // Fetch User
                const userData = await userService.getUserById(id);
                setUser(userData);

                // Fetch Liked Songs
                const songs = await songService.getLikedSongsByUserId(userData.username);
                setLikedSongs(songs);

                // Fetch Liked Albums
                const albums = await albumService.getLikedAlbumsByUserId(userData.username);
                setLikedAlbums(albums);

                // Fetch User Playlists
                const userPlaylists = await playlistService.getPlaylistsByUserId(userData.username);
                setPlaylists(userPlaylists);
            } catch (error) {
                console.error("Error fetching user details:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchUserData();
    }, [id]);

    if (loading) return <div className="text-center mt-5">Loading...</div>;
    if (!user) return <div className="text-center mt-5">Error: User not found</div>;

    return (
        <div className="container mt-5 p-4" style={{ maxWidth: "900px" }}>
            <h2 className="text-center mb-4">{user.username}</h2>
            <h6 className="text-center mb-4">{user.role}</h6>

            {/* Liked Songs Section */}
            <div className="card shadow-sm p-3 mb-4">
                <h3 className="text-center">Liked Songs</h3>
                {likedSongs.length > 0 ? (
                    <div className="row">
                        {likedSongs.map((song) => (
                            <div key={song.id} className="col-12 mb-3">
                                <Link to={`/songs/${song.id}`} className="list-group-item text-decoration-none">
                                    <div className="d-flex align-items-center">
                                        <img
                                            src={song.album?.imageUrl || "/default-song.jpg"}
                                            alt={song.title}
                                            className="rounded me-3"
                                            style={{ width: "50px", height: "50px", objectFit: "cover" }}
                                        />
                                        <div>
                                            <h6 className="mb-0">{song.title}</h6>
                                            <small className="text-muted">{song.artist?.name || "Unknown Artist"}</small>
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="text-center text-muted">No liked songs.</p>
                )}
            </div>

            {/* Liked Albums Section */}
            <div className="card shadow-sm p-3 mb-4">
                <h3 className="text-center">Liked Albums</h3>
                {likedAlbums.length > 0 ? (
                    <div className="row">
                        {likedAlbums.map((album) => (
                            <div key={album.id} className="col-md-6 mb-3">
                                <Link to={`/albums/${album.id}`} className="list-group-item text-decoration-none">
                                    <div className="d-flex align-items-center">
                                        <img
                                            src={album.imageUrl || "/default-album.jpg"}
                                            alt={album.title}
                                            className="rounded me-3"
                                            style={{ width: "50px", height: "50px", objectFit: "cover" }}
                                        />
                                        <div>
                                            <h6 className="mb-0">{album.title}</h6>
                                            <small className="text-muted">{album.artist?.name || "Unknown Artist"}</small>
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="text-center text-muted">No liked albums.</p>
                )}
            </div>

            {/* Created Playlists Section */}
            <div className="card shadow-sm p-3">
                <h3 className="text-center">Playlists</h3>
                {playlists.length > 0 ? (
                    <div className="row">
                        {playlists.map((playlist) => (
                            <div key={playlist.id} className="col-md-6 mb-3">
                                <Link to={`/playlists/${playlist.id}`} className="list-group-item text-decoration-none">
                                    <div className="d-flex align-items-center">
                                        <img
                                            src={playlist.imageUrl || "/default-playlist.jpg"}
                                            alt={playlist.name}
                                            className="rounded me-3"
                                            style={{ width: "50px", height: "50px", objectFit: "cover" }}
                                        />
                                        <div>
                                            <h6 className="mb-0">{playlist.name}</h6>
                                            <small className="text-muted">{playlist.songs.length} songs</small>
                                        </div>
                                    </div>
                                </Link>
                            </div>
                        ))}
                    </div>
                ) : (
                    <p className="text-center text-muted">No created playlists.</p>
                )}
            </div>
        </div>
    );
};

export default DetailsUser;
