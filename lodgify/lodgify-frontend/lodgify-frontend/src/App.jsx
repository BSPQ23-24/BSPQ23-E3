import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login.jsx';
import Register from './Register.jsx';
import Home from './Home.jsx';
import ResidenceRegistration from './ResidenceRegistration.jsx';
import Profile from './Profile.jsx';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<Home />} />
                <Route path="/registerResidence" element={<ResidenceRegistration />} />
                <Route path="/Profile" element={<Profile />} />
            </Routes>
        </Router>
    );
};

export default App;
