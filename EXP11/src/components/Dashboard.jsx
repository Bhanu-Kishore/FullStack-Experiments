import React, { useState } from 'react';
import LocalUserList from './LocalUserList';
import UserList from './UserList';
import FakePostList from './FakePostList';

const Dashboard = () => {
    const [activeComponent, setActiveComponent] = useState('LOCAL');

    const renderComponent = () => {
        switch (activeComponent) {
            case 'LOCAL': return <LocalUserList />;
            case 'API': return <UserList />;
            case 'POSTS': return <FakePostList />;
            default: return <LocalUserList />;
        }
    };

    return (
        <div className="dashboard">
            <header className="dashboard-header">
                <h1>API Integration Dashboard</h1>
                <nav className="nav-menu">
                    <button 
                        className={activeComponent === 'LOCAL' ? 'active' : ''} 
                        onClick={() => setActiveComponent('LOCAL')}
                    >
                        Local Users
                    </button>
                    <button 
                        className={activeComponent === 'API' ? 'active' : ''} 
                        onClick={() => setActiveComponent('API')}
                    >
                        Users API
                    </button>
                    <button 
                        className={activeComponent === 'POSTS' ? 'active' : ''} 
                        onClick={() => setActiveComponent('POSTS')}
                    >
                        Fake API Posts
                    </button>
                </nav>
            </header>
            <main className="content-area">
                {renderComponent()}
            </main>
        </div>
    );
};

export default Dashboard;
