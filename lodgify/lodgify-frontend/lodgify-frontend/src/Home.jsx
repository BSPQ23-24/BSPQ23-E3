import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import logo from './assets/lodgify_logo.png';
import apartamento from './assets/apartamento.jpg';

const HomePage = () => {
    const [place, setPlace] = useState('');
    const [residences, setResidences] = useState([]);

    const handleSearch = async () => {
        try {
            const response = await fetch(`http://localhost:8080/rest/residence/search?address=${place}`)
            if (!response.ok) {
                throw new Error(response);
            }
            const data = await response.json();
            setResidences(data);
        } catch (error) {
            console.error('Error:', error);
        }
    };
    
    return (
        <div className="flex flex-col items-center">
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
            <div className="bg-gray-100 m-20 mb-8 p-8 rounded-lg shadow-top text-center w-4/5">
                <h1 className = "font-bold text-xl">¡FIND ACCOMODATIONS USING THE FILTER!</h1>
                <form onSubmit={handleSearch}>
                    <div className="flex w-full justify-center">
                        <input
                            type="text"
                            value={place}
                            onChange={(e) => setPlace(e.target.value)}
                            placeholder="Place"
                            className="m-4 mt-8 p-2 w-72 rounded-md border"
                        />
                    </div>
                    <div className="flex w-full justify-center">
                        <input
                            type="text"
                            placeholder="Arrival date (DD/MM/YY)"
                            className="m-4 p-2 w-56 rounded-md border"
                        />
                        <input
                            type="text"
                            placeholder="Departure date (DD/MM/YY)"
                            className="m-4 p-2 w-56 rounded-md border"
                        />
                    </div>
                </form>
                <div className="flex mt-4 W-4/5 justify-center">
                    <button onClick={handleSearch} className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-xl">
                        Search
                    </button>
                </div>
            </div>

            <div>
                {residences.map((residence) => (
                        <div key={residence.id} className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center w-4/5">
                            <img src={apartamento} alt="Apartamento" className='mx-auto h-10 md:h-48 w-96 p-4 rounded-3xl' />
                            <p className="p-4">Location: {residence.residence_address}</p>
                            <p className="p-4">Residence type: {residence.residence_type}</p>
                            <p className="p-4">Price: {residence.price}€</p>
                            <button className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-xl pt-2">
                                Know more
                            </button>
                        </div>
                    ))
                }
            </div>

            <footer>
                <p>&copy; 2024 Lodgify. All rights reserved.</p>
            </footer>
        </div>
    );
};

export default HomePage;