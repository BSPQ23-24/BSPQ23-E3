// Register.jsx
import React, { useState } from 'react';
import logo from './assets/lodgify.png';

const Register = ({ showLoginForm }) => { // Recibe la función showLoginForm como prop
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState(0);
    const [redirectToHome, setRedirectToHome] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const user = {
            username,
            password,
            name,
            surname,
            phone_number: parseInt(phoneNumber, 10),
            email,
        };

        try {
            const response = await fetch('http://localhost:8080/rest/user/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user),
            });

            if (response.ok) {
                console.log("User registered successfully!");
                setRedirectToHome(true);
            } else {
                console.error("Failed to register user");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    // Función para manejar el clic en el enlace "Back"
    const handleBack = (e) => {
        e.preventDefault(); // Evita que se siga el enlace
        showLoginForm(); // Llama a la función showLoginForm pasada como prop
    };

    if (redirectToHome) {
        return <Redirect to="/#" />;
    }

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                    <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="text"
                            placeholder="Name*"
                            className="mb-2 p-2 rounded-md border"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="text"
                            placeholder="Surname*"
                            className="mb-2 p-2 rounded-md border"
                            value={surname}
                            onChange={(e) => setSurname(e.target.value)}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="text"
                            placeholder="Username*"
                            className="mb-2 p-2 rounded-md border"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="password"
                            placeholder="Password*"
                            className="mb-2 p-2 rounded-md border"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="text"
                            placeholder="Email*"
                            className="mb-2 p-2 rounded-md border"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <input
                            type="number"
                            placeholder="Phone number*"
                            className="mb-2 p-2 rounded-md border"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value === '' ? '' : Number(e.target.value))}
                        />
                    </div>
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                            Register
                        </button>
                    </div>
                </form>
                <div className="flex mt-4">
                    {/* Llama a la función handleBack al hacer clic */}
                    <p><a href="#" className="font-bold text-blue-900" onClick={handleBack}>Back</a></p>
                </div>
            </div>
        </div>
    );
};


export default Register;
