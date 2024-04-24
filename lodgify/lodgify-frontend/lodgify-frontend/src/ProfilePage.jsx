import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useUser } from './contexts/UserContext.jsx';
import logo from './assets/lodgify_logo.png';

const ProfilePage = () => {
    const { user } = useUser();
    const [residence_address, setResidence_address] = useState('');
    const [residence_type, setResidence_type] = useState('');
    const [n_rooms, setN_rooms] = useState(0);
    const [price, setPrice] = useState(0);
    const [user_id, setUser_id] = useState(0);
    const [error, setError] = useState('');
    const [warning, setWarning] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        const residence = {
            residence_address,
            residence_type,
            n_rooms,
            price,
            user_id,
        };

        try {
            setUser_id(user.id)
            const response = await fetch('http://localhost:8080/rest/residence/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(residence),
            });
            const responseBody = await response.text();
            if (response.ok) {
                setResidence_address("")
                setResidence_type("")
                setN_rooms("")
                setPrice("")
                setWarning("Residence registered successfully!")
                console.log("Residence registered successfully!");
            } else if (responseBody == "Fill all the data!"){
                console.error("Failed to register user because all the data has not been filled")
                setError("Fill all the data!")
            } else {
                console.error(responseBody);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className = "text-center">
            <nav className="bg-gray-50 p-4 shadow-md w-full">
                <div className="flex justify-between items-center">
                    <img src={logo} alt="Lodgify" className="h-5 md:h-12 px-12" />
                    <ul className="flex">
                        <li><Link to="/home" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">HOME</Link></li>
                        <li className="mr-4 px-12"><a href="#" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold">LISTINGS</a></li>
                        <li className="mr-4 px-12"><a href="#" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold">ABOUT US</a></li>
                        <li><Link to="/profile" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">MY PROFILE</Link></li>
                    </ul>
                </div>
            </nav>
            <h1 className = "font-bold text-3xl mt-8">Hi, {user ? user.name : 'Invitado'}!</h1>
            {user ? (
                user.user_type === 'Host' ?
                <div>
                    <p className = "font-semibold text-xl mt-8">As you are a host, you can add residences!</p> 
                    <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
                        <form onSubmit={handleSubmit}>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="Residence address*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={residence_address}
                                    onChange={(e) => setResidence_address(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="Residence type*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={residence_type}
                                    onChange={(e) => setResidence_type(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="number"
                                    placeholder="Nº rooms*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={n_rooms}
                                    onChange={(e) => setN_rooms(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="number"
                                    placeholder="Price*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={price}
                                    onChange={(e) => setPrice(e.target.value)}
                                />
                            </div>
                            {error && <div className="text-red-500 mb-2">{error}</div>}
                            {warning && <div className="text-green-500 mb-2">{warning}</div>}
                            <div className="flex mt-4 justify-center">
                                <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                                    Register residence
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                :
                <p className = "font-semibold text-xl mt-8">Sorry, you are not a host, so you can't add any residence!</p>
            ) : null}
        </div>
    );
};

export default ProfilePage;
