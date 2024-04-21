// Register.jsx
import React, { useState } from 'react';
import logo from './assets/lodgify.png';

<<<<<<< Updated upstream
const Register = () => {
=======
const Register = ({ showLoginForm }) => { // Recibe la función showLoginForm como prop
    const [userType, setUserType] = useState('User');
>>>>>>> Stashed changes
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
<<<<<<< Updated upstream
    const [phoneNumber, setPhoneNumber] = useState(0);
=======
    const [phoneNumber, setPhoneNumber] = useState('');
    const [idCard, setIdCard] = useState('');
    const [bankAccount, setBankAccount] = useState(0);
    const [socialSN, setSocialSN] = useState(0);
    const [address, setAddress] = useState('');
    const [error, setError] = useState('');
    const [warning, setWarning] = useState('');
>>>>>>> Stashed changes

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const user = {
            username,
            password,
            name,
            surname,
            phone_number: phoneNumber,
            email,
            user_type: userType,
            id_card: idCard,
            bank_account: bankAccount,
            social_SN: socialSN,
            address,
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
<<<<<<< Updated upstream
                console.log("User registered succesfully!");
=======
                setError("")
                setName("")
                setSurname("")
                setUsername("")
                setPassword("")
                setEmail("")
                setPhoneNumber("")
                setUserType('User');
                setIdCard('');
                setBankAccount('');
                setSocialSN('');
                setAddress('');
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
>>>>>>> Stashed changes
            } else {
                console.error(responseBody);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className="flex flex-col items-center justify-center h-screen mt-10 mb-10">
            <div className="bg-white p-8 rounded-lg shadow-md text-center">
                <div className='flex justify-center mb-6'>
                    <img src={logo} alt="Logo" className='h-10 md:h-48' />
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="flex mt-4 justify-center">
                        <select className="mb-2 p-2 rounded-md border"
                            value={userType}
                            onChange={(e) => setUserType(e.target.value)}
                        >
                            <option value="User">Traveller</option>
                            <option value="Host">Host</option>
                        </select>
                    </div>
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
                            onChange={(e) => setPhoneNumber(e.target.value)}
                        />
                    </div>
<<<<<<< Updated upstream
=======
                    {userType === "Host" && (
                        <>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="ID Card*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={idCard}
                                    onChange={(e) => setIdCard(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="Bank account*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={bankAccount}
                                    onChange={(e) => setBankAccount(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="Social SN*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={socialSN}
                                    onChange={(e) => setSocialSN(e.target.value)}
                                />
                            </div>
                            <div className="flex mt-4 justify-center">
                                <input
                                    type="text"
                                    placeholder="Address*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={address}
                                    onChange={(e) => setAddress(e.target.value)}
                                />
                            </div>
                        </>
                    )}
                    {error && <div className="text-red-500 mb-2">{error}</div>}
                    {warning && <div className="text-green-500 mb-2">{warning}</div>}
>>>>>>> Stashed changes
                    <div className="flex mt-4 justify-center">
                        <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div >
    );
};

export default Register;
