import React, { useState } from 'react';
import logo from './assets/lodgify.png';

const Login = () => {

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                  <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <div className="flex justify-center mt-4">
                    <input
                        type="text"
                        placeholder="Username"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex justify-center mt-4">
                    <input
                        type="password"
                        placeholder="Password"
                        className="mb-2 p-2 rounded-md border"
                    />
                </div>
                <div className="flex mt-4 justify-center">
                    <button className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-md">
                        Log in
                    </button>
                </div>
                <div className="flex mt-4">
                    <p>Do you still not have an account? <a href="" className="font-bold text-blue-900">Register now!</a></p>
                </div>
            </div>
        </div >
    );

};

export default Login;
