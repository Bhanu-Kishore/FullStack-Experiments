import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FakePostList = () => {
    const [posts, setPosts] = useState([]);
    const [filteredPosts, setFilteredPosts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedUser, setSelectedUser] = useState('All');

    const fetchPosts = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get('https://dummyjson.com/posts');
            setPosts(response.data.posts);
            setFilteredPosts(response.data.posts);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPosts();
    }, []);

    useEffect(() => {
        if (selectedUser === 'All') {
            setFilteredPosts(posts);
        } else {
            setFilteredPosts(posts.filter(post => post.userId === parseInt(selectedUser)));
        }
    }, [selectedUser, posts]);

    const userIds = [...new Set(posts.map(post => post.userId))].sort((a, b) => a - b);

    if (loading) return <div className="loading">Loading fake posts...</div>;
    if (error) return <div className="error">Error: {error}</div>;

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Fake API Posts</h2>
                <div className="controls">
                    <select 
                        className="dropdown" 
                        value={selectedUser} 
                        onChange={(e) => setSelectedUser(e.target.value)}
                    >
                        <option value="All">All Users</option>
                        {userIds.map(id => (
                            <option key={id} value={id}>User ID: {id}</option>
                        ))}
                    </select>
                    <button className="refresh-btn" onClick={fetchPosts}>Refresh</button>
                </div>
            </div>
            <div className="card-grid">
                {filteredPosts.length > 0 ? (
                    filteredPosts.map(post => (
                        <div className="card" key={post.id}>
                            <h3>{post.title}</h3>
                            <p className="card-body">{post.body}</p>
                            <span className="user-tag">User {post.userId}</span>
                        </div>
                    ))
                ) : (
                    <p className="no-data">No posts found for this user.</p>
                )}
            </div>
        </div>
    );
};

export default FakePostList;
