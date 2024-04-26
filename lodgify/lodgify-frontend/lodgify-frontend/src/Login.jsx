import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import logo from './assets/lodgify.png';
import { useUser } from './contexts/UserContext';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { setUser } = useUser();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const credentials = { username, password };

        try {
            const response = await fetch('http://localhost:8080/rest/user/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(credentials),
            });

            if (response.ok) {
                const userData = await response.json();
                console.log(userData.username);
                console.log(userData)
                setUser(userData);
                console.log("User logged in successfully!");
                navigate('/home');
            } else {
                console.log(username)
                console.log(response.json())
                console.error("Failed to log in user");
                setError('Invalid username or password!');
            }
        } catch (error) {
            console.error("Error fetching response from backend API:", error);
            setError('Login failed due to server error');
        }
    };

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                    <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="flex justify-center mt-4">
                        <input type="text" placeholder="Username" className="mb-2 p-2 rounded-md border"
                            value={username} onChange={(e) => setUsername(e.target.value)} />
                    </div>
                    <div className="flex justify-center mt-4">
                        <input type="password" placeholder="Password" className="mb-2 p-2 rounded-md border"
                            value={password} onChange={(e) => setPassword(e.target.value)} />
                    </div>
                    {error && <div className="text-red-500 mb-2">{error}</div>}
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-md">
                            Log in
                        </button>
                    </div>
                </form>
                <div className="flex mt-4">
                    <p>Do you still not have an account?
                        <button className="font-bold text-blue-900" onClick={() => navigate('/register')}>
                            Register now!
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default Login;
