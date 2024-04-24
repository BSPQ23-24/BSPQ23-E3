import React, { useState } from 'react';
import { useUser } from './contexts/UserContext.jsx';

const ProfilePage = () => {
    const { user } = useUser();
    const [residence_address, setResidence_address] = useState('');
    const [residence_type, setResidence_type] = useState('');
    const [n_rooms, setN_rooms] = useState(0);
    const [price, setPrice] = useState(0);

    return (
        <div className = "text-center">
            <h1 className = "font-bold text-3xl mt-8">Hi, {user ? user.name : 'Invitado'}!</h1>
            {user ? (
                user.user_type === 'Host' ?
                <div>
                    <p className = "font-semibold text-xl mt-8">As you are a host, you can add residences!</p> 
                    <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
                        <form>
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
