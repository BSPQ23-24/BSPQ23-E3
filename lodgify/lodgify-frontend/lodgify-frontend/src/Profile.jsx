import React, { useState, useEffect  } from 'react';
import { Link } from 'react-router-dom';
import { useUser } from './contexts/UserContext.jsx';
import logo from './assets/lodgify_logo.png';
import apartamento from './assets/apartamento.jpg';

const Profile = () => {
    const { user, setUser } = useUser();
    const [place, setPlace] = useState('Bilbao');
    const [residences, setResidences] = useState([]);
    const [userType, setUserType] = useState(user.user_type);
    const [name, setName] = useState(user.name);
    const [surname, setSurname] = useState(user.surname);
    const [username, setUsername] = useState(user.username);
    const [password, setPassword] = useState(user.password);
    const [email, setEmail] = useState(user.email);
    const [phoneNumber, setPhoneNumber] = useState(user.phone_number);
    const [idCard, setIdCard] = useState(user.id_card);
    const [bankAccount, setBankAccount] = useState(user.bank_account);
    const [socialSN, setSocialSN] = useState(user.social_sn);
    const [address, setAddress] = useState(user.address);
    const [error, setError] = useState('');
    const [warning, setWarning] = useState('');

    useEffect(() => {
        const handleSearch = async () => {
            try {
                console.log("Ha entrado");
                console.log(user.username);
                const response = await fetch(`http://localhost:8080/rest/residence/searchID?user_id=${user.username}`);
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                console.log(data);
                setResidences(data);
            } catch (error) {
                console.error('Error:', error);
            }
        };
    
        console.log(user.id);
        handleSearch();
    }, [user.username]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const user_profile = {
            user_type: userType,
            name,
            surname,
            username,
            password,
            email,
            phone_number: phoneNumber,
            id_card: idCard,
            bank_account: bankAccount,
            social_SN: socialSN,
            address,
        };

        try {
            console.log(user_profile);
            const response = await fetch('http://localhost:8080/rest/user/modify', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(user_profile),
            });
            const responseBody = await response.text();
            if (response.ok) {
                setUser(user_profile);
                setName(name);
                setSurname(surname);
                setEmail(email);
                setPhoneNumber(phoneNumber);
                setIdCard(idCard);
                setBankAccount(bankAccount);
                setSocialSN(socialSN);
                setAddress(address);
                setWarning("User data changed successfully!");
                console.log("User data changed successfully!");
            } else {
                console.error(responseBody);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className = "text-center mx-auto">
            <nav className="bg-gray-50 p-4 shadow-md w-full">
                <div className="flex justify-between items-center">
                    <img src={logo} alt="Lodgify" className="h-5 md:h-12 px-12" />
                    <ul className="flex">
                        <li><Link to="/home" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">HOME</Link></li>
                        <li className="mr-4 px-12"><a href="#" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold">LISTINGS</a></li>
                        <li className="mr-4 px-12"><a href="#" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold">ABOUT US</a></li>
                        <li><Link to="/registerResidence" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">REGISTER RESIDENCE</Link></li>
                        <li><Link to="/profile" style={{ color: 'rgb(4, 18, 26)'}} className="font-bold px-12">MY PROFILE</Link></li>
                    </ul>
                </div>
            </nav>
            <h1 className = "font-bold text-3xl mt-8 mb-8">Modify your data</h1>
            <div className = "bg-gray-100 p-8 rounded-lg shadow-top text-center mx-auto w-2/5">
                <form onSubmit={handleSubmit}>
                    <div className="mt-4 justify-center">
                       <p className = "font-bold text-md pb-2">Name</p>
                       <input
                            type="text"
                            placeholder="Name*"
                            className="mb-2 p-2 rounded-md border"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                        />
                    </div>
                    <div className="mt-4 justify-center">
                       <p className = "font-bold text-md pb-2">Surname</p>
                        <input
                            type="text"
                            placeholder="Surname*"
                            className="mb-2 p-2 rounded-md border"
                            value={surname}
                            onChange={(e) => setSurname(e.target.value)}
                        />
                    </div>
                    <div className="mt-4 justify-center">
                       <p className = "font-bold text-md pb-2">Email</p>
                        <input
                            type="text"
                            placeholder="Email*"
                            className="mb-2 p-2 rounded-md border"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>
                    <div className="mt-4 justify-center">
                       <p className = "font-bold text-md pb-2">Phone number</p>
                        <input
                            type="number"
                            placeholder="Phone number*"
                            className="mb-2 p-2 rounded-md border"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                        />
                    </div>
                    {userType === "Host" && (
                        <>
                            <div className="mt-4 justify-center">
                                <p className = "font-bold text-md pb-2">ID Card</p>
                                <input
                                    type="text"
                                    placeholder="ID Card*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={idCard}
                                    onChange={(e) => setIdCard(e.target.value)}
                                />
                            </div>
                            <div className="mt-4 justify-center">
                                <p className = "font-bold text-md pb-2">Bank account</p>
                                <input
                                    type="text"
                                    placeholder="Bank account*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={bankAccount}
                                    onChange={(e) => setBankAccount(e.target.value)}
                                />
                            </div>
                            <div className="mt-4 justify-center">
                                <p className = "font-bold text-md pb-2">Social SN</p>
                                <input
                                    type="text"
                                    placeholder="Social SN*"
                                    className="mb-2 p-2 rounded-md border"
                                    value={socialSN}
                                    onChange={(e) => setSocialSN(e.target.value)}
                                />
                            </div>
                            <div className="mt-4 justify-center">
                                <p className = "font-bold text-md pb-2">Address</p>
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
                    <div className="flex mt-4 justify-center">
                        <button onClick = {handleSubmit} type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                            Change data
                        </button>
                    </div>
                </form>
            </div>
            <div className="mt-4">
                <Link to="/passwordRecovery" className="text-blue-500 hover:text-blue-800">Forgot your password? Click here to recover it.</Link>
            </div>
            <h1 className = "font-bold text-3xl mt-8 mb-8">Your residences</h1>
                {residences.map((residence) => (
                            <div key={residence.id} className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center mx-auto w-4/5">
                                <img src={apartamento} alt="Apartamento" className='mx-auto h-10 md:h-48 w-96 p-4 rounded-3xl' />
                                <p className="p-4">Location: {residence.residence_address}</p>
                                <p className="p-4">Residence type: {residence.residence_type}</p>
                                <p className="p-4">Price: {residence.price}€</p>
                            </div>
                        ))
                }
        </div>
    );
};

export default Profile;
