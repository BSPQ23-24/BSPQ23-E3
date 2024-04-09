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
    const [error, setError] = useState('');
    const [warning, setWarning] = useState('');

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
            const responseBody = await response.text();
            if (response.ok) {
                setError("")
                setName("")
                setSurname("")
                setUsername("")
                setPassword("")
                setEmail("")
                setPhoneNumber("")
                setWarning("User registered successfully!")
                console.log("User registered successfully!");
            } else if (responseBody == "User already exists!"){
                console.error("Failed to register user because the user already exists");
                setError("User already exists!")
            } else if (responseBody == "Fill all the data!"){
                console.error("Failed to register user because all the data has not been filled")
                setError("Fill all the data!")
            } else if (responseBody == "Not valid phone number!"){
                console.error("Failed to register user because the phone number must have 9 digits")
                setError("Not valid phone number!")
            } else if (responseBody == "Not valid email!"){
                console.error("Failed to register user because of email");
                setError("Not valid email!")
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
                    {error && <div className="text-red-500 mb-2">{error}</div>}
                    {warning && <div className="text-green-500 mb-2">{warning}</div>}
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                            Register
                        </button>
                    </div>
                </form>
                <div className="flex mt-4">
                    {/* Llama a la función handleBack al hacer clic */}
                    <p><a href="#" className="font-bold text-blue-950 justify-center text-center" onClick={handleBack}>{'<'}Back</a></p>
                </div>
            </div>
        </div>
    );
};


export default Register;
