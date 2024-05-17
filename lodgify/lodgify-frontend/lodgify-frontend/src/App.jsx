import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login.jsx';
import Register from './Register.jsx';
import Home from './Home.jsx';
import Bookings from "./Bookings.jsx";
import ResidenceRegistration from './ResidenceRegistration.jsx';
import Profile from './Profile.jsx';
import PasswordRecoveryPage from './PasswordRecovery.jsx';
import ReservationPage from './ReservationPage';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<Home />} />
                <Route path="/registerResidence" element={<ResidenceRegistration />} />
                <Route path="/Profile" element={<Profile />} />
                <Route path="/passwordRecovery" element={<PasswordRecoveryPage />} />
                <Route path="/reservation" element={<ReservationPage />} />
                <Route path="/bookings" element={<Bookings />} />
                <Route path="/payment" element={<PaymentForm />} />
            </Routes>
        </Router>
    );
};

export default App;
