import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import logo from './assets/lodgify_logo.png';

const ReservationPage = () => {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [user, setUser] = useState(null);
    const [residence, setResidence] = useState(null);

    useEffect(() => {
        // Fetch user data
        fetch('http://localhost:8080/rest/user')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }
                return response.json();
            })
            .then(userData => {
                setUser(userData);
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
            });

        // Fetch residence data
        fetch('http://localhost:8080/rest/residence')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch residence data');
                }
                return response.json();
            })
            .then(residenceData => {
                setResidence(residenceData);
            })
            .catch(error => {
                console.error('Error fetching residence data:', error);
            });
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!user || !residence) {
            setError('Failed to fetch user or residence data');
            return;
        }

        // Replace user.id, hostId, and residenceId with actual IDs
        const bookingData = {
            start_date: startDate,
            end_date: endDate,
            traveler_id: user.id,
            host_id: residence.user_user_username, // Assuming you have user ID of the host available
            residence_id: residence.id // Assuming you have residence ID available
        };

        // Make the API call to save the booking data
        try {
            const response = await fetch('http://localhost:8080/rest/booking/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(bookingData),
            });

            if (!response.ok) {
                throw new Error('Failed to book residence');
            }

            // Handle success
            setStartDate('');
            setEndDate('');
            setSuccess('Residence booked successfully!');
        } catch (error) {
            // Handle error
            setError('Failed to book residence');
            console.error('Error:', error);
        }
    };

    return (
        <div className="text-center">
            <nav className="bg-gray-50 p-4 shadow-md w-full">
                <div className="flex justify-between items-center">
                    <img src={logo} alt="Lodgify" className="h-5 md:h-12 px-12" />
                    <ul className="flex">
                        <li><Link to="/home" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">HOME</Link></li>
                        {/* Add other navigation links as needed */}
                    </ul>
                </div>
            </nav>
            <h1 className="font-bold text-3xl mt-8">Reservation Process</h1>
            <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
                <form onSubmit={handleSubmit}>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="text"
                            placeholder="Start Date (DD/MM/YY)"
                            className="m-4 p-2 w-56 rounded-md border"
                            value={startDate}
                            onChange={(e) => setStartDate(e.target.value)}
                        />
                        <input
                            type="text"
                            placeholder="End Date (DD/MM/YY)"
                            className="m-4 p-2 w-56 rounded-md border"
                            value={endDate}
                            onChange={(e) => setEndDate(e.target.value)}
                        />
                    </div>
                    {error && <div className="text-red-500 mb-2">{error}</div>}
                    {success && <div className="text-green-500 mb-2">{success}</div>}
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                            Book Residence
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ReservationPage;
