import React from 'react';
import logo from './assets/lodgify.png';

const HomePage = () => {
    return (
        <div>
            {/* Navbar */}
            <nav className="navbar">
                <img src={logo} alt="Lodgify" className="logo" />
                <ul className="nav-links">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Listings</a></li>
                    <li><a href="#">About Us</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
            </nav>

            {/* Hero Section */}
            <div className="hero">
                <h1>Welcome to Lodgify</h1>
                <p>Your one-stop destination for amazing stays</p>
                <button className="btn">Explore Now</button>
            </div>

            {/* Featured Listings Section */}
            <div className="listings">
                <h2>Featured Listings</h2>
                {}
            </div>

            {/* About Us Section */}
            <div className="about">
                <h2>About Us</h2>
                <p>Lodgify</p>
            </div>

            {/* Contact Section */}
            <div className="contact">
                <h2>Contact Us</h2>
                <p>If you have any questions or inquiries, feel free to reach out to us.</p>
                <button className="btn">Contact Us</button>
            </div>

            {/* Footer */}
            <footer>
                <p>&copy; 2024 Lodgify. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default HomePage;