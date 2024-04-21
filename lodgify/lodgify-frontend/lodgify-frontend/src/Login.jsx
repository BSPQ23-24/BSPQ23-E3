// Login.jsx
import React, { useState } from 'react';
import logo from './assets/lodgify.png';
import Register from './Register'; // Importa el componente Register
import Home from './Home';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [showRegister, setShowRegister] = useState(false);
    const [showHome, setShowHome] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const credentials = {
            username,
            password,
        };

        try {
            const response = await fetch('http://localhost:8080/rest/user/login', { 
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(credentials),
            });

            console.log(response)
            
            if (response.ok) {
                console.log("User logged in successfully!");
                showHomeComp();
                // SWITCH TO HOME PAGE; HANDLE COOKIES, JWT TOKENS
            } else {
                // DISPLAY AN ERROR
                console.error("Failed to log in user");
                setError('Invalid username or password!')
            }
        } catch (error) {
            console.error("Error fetching response from backend API:", error);
        }
    };

    // Función para mostrar el componente Register
    const showRegisterForm = () => {
        setShowRegister(true);
    };

    // Función para mostrar el componente Home
    const showHomeComp = () => {
        setShowHome(true);
    };

    // Función para volver al formulario de inicio de sesión
    const showLoginForm = () => {
        setShowRegister(false);
    };

    if(showHome) {
        return <Home showHomeComp={showHomeComp} />;
    }
    
    // Si showRegister es true, renderiza el componente Register
    if (showRegister) {
        return <Register showLoginForm={showLoginForm} />;
    }

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                    <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="flex justify-center mt-4">
                        <input
                            type="text"
                            placeholder="Username"
                            className="mb-2 p-2 rounded-md border"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className="flex justify-center mt-4">
                        <input
                            type="password"
                            placeholder="Password"
                            className="mb-2 p-2 rounded-md border"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    {error && <div className="text-red-500 mb-2">{error}</div>}
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-md">
                            Log in
                        </button>
                    </div>
                </form>
                <div className="flex mt-4">
                    {/* Llama a la función showRegisterForm al hacer clic */}
                    <p>Do you still not have an account? <a href="#" className="font-bold text-blue-900" onClick={showRegisterForm}>Register now!</a></p>
                </div>
            </div>
        </div>
    );
};

export default Login;
