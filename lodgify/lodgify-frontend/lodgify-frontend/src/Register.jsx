import React, { useState } from 'react';
import logo from './assets/lodgify.png';

const Register = () => {
    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                    <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="text"
                        placeholder="Name*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="text"
                        placeholder="Surname*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="text"
                        placeholder="Username*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="password"
                        placeholder="Password*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="text"
                        placeholder="Email*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <input
                        type="number"
                        placeholder="Phone number*"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <button className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                        Register
                    </button>
                </div>
            </div>
        </div >
    );

};

export default Register;
