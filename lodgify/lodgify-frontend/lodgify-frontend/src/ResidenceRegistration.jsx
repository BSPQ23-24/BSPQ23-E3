import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import en from './translations/en.json';
import es from './translations/es.json';
import lt from './translations/lt.json';
import { useUser } from './contexts/UserContext.jsx';
import { useLocale } from './contexts/LocaleContext.jsx';
import logo from './assets/lodgify_logo.png';

const ResidenceRegistration = () => {
    const { user } = useUser();
    const [residence_address, setResidence_address] = useState('');
    const [residence_type, setResidence_type] = useState('');
    const [n_rooms, setN_rooms] = useState(0);
    const [price, setPrice] = useState(0);
    const [user_id, setUser_id] = useState(user.username);
    const [error, setError] = useState('');
    const [warning, setWarning] = useState('');
    const { locale, setLocale } = useLocale();
  
    const translations = {
      en,
      es,
      lt
    }[locale];

    const handleSubmit = async (e) => {
        e.preventDefault();
        const residence = {
            residence_address,
            residence_type,
            n_rooms,
            price,
            user_id,
        };

        try {
            console.log(residence);
            const response = await fetch('http://localhost:8080/rest/residence/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(residence),
            });
            const responseBody = await response.text();
            if (response.ok) {
                setResidence_address("")
                setResidence_type("")
                setN_rooms("")
                setPrice("")
                setWarning(translations.residenceRegistration.successMessage);
                console.log("Residence registered successfully!");
            } else if (responseBody == "Fill all the data!"){
                console.error("Failed to register user because all the data has not been filled")
                setError(translations.residenceRegistration.errors.fillData);
            } else {
                console.error(responseBody);
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className = "text-center">
        <nav className="bg-gray-50 p-4 shadow-md w-full">
        <div className="flex justify-between items-center">
          <img src={logo} alt="Lodgify" className="h-5 md:h-12 px-12" />
          <ul className="flex">
            <li>
              <Link
                to="/home"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.home.homeNav}
              </Link>
            </li>
            {user ? (
                user.user_type === 'Host' ?
              <li>
                <Link
                  to="/registerResidence"
                  style={{ color: "rgb(4, 18, 26)" }}
                  className="font-bold px-12"
                >
                  {translations.home.residenceRegNav}
                </Link>
              </li>
            : null): null}
            <li>
              <Link
                to="/bookings"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.home.myBookings}
              </Link>
            </li>
            <li>
              <Link
                to="/profile"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.home.profileNav}
              </Link>
            </li>
            <li>
              <Link
                to="/"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.logout}
              </Link>
            </li>
          </ul>
        </div>
      </nav>
            <h1 className = "font-bold text-3xl mt-8">{translations.residenceRegistration.welcome}{user ? user.name : 'Invitado'}!</h1>
            {user ? (
                user.user_type === 'Host' ?
                <div>
                    <p className = "font-semibold text-xl mt-8">{translations.residenceRegistration.hostMessage}</p> 
                    <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
                        <form onSubmit={handleSubmit}>
                            <div className="flex-col mt-4 justify-center items-center">
                              <p className="font-bold text-md pb-2">{translations.residenceRegistration.placeholders.residenceAddress}</p>
                                <input
                                    type="text"
                                    placeholder={translations.residenceRegistration.placeholders.residenceAddress}
                                    className="mb-2 p-2 rounded-md border"
                                    value={residence_address}
                                    onChange={(e) => setResidence_address(e.target.value)}
                                />
                            </div>
                            <div className="flex-col mt-4 justify-center items-center">
                              <p className="font-bold text-md pb-2">{translations.residenceRegistration.placeholders.residenceType}</p>
                              <select
                                className="mb-2 p-2 rounded-md border"
                                value={residence_type}
                                onChange={(e) => setResidence_type(e.target.value)}
                              >
                                <option value=""></option>
                                <option value="Flat">{translations.residenceRegistration.typeResidenceFlat}</option>
                                <option value="House">{translations.residenceRegistration.typeResidenceHouse}</option>
                              </select>
                            </div>
                            <div className="flex-col mt-4 justify-center items-center">
                              <p className="font-bold text-md pb-2">{translations.residenceRegistration.placeholders.numberOfRooms}</p>
                                <input
                                    type="number"
                                    placeholder={translations.residenceRegistration.placeholders.numberOfRooms}
                                    className="mb-2 p-2 rounded-md border"
                                    value={n_rooms}
                                    onChange={(e) => setN_rooms(e.target.value)}
                                />
                            </div>
                            <div className="flex-col mt-4 justify-center items-center">
                              <p className="font-bold text-md pb-2">{translations.residenceRegistration.placeholders.price}</p>
                                <input
                                    type="number"
                                    placeholder={translations.residenceRegistration.placeholders.price}
                                    className="mb-2 p-2 rounded-md border"
                                    value={price}
                                    onChange={(e) => setPrice(e.target.value)}
                                />
                            </div>
                            {error && <div className="text-red-500 mb-2">{error}</div>}
                            {warning && <div className="text-green-500 mb-2">{warning}</div>}
                            <div className="flex mt-4 justify-center">
                                <button type="submit" className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md">
                                    {translations.residenceRegistration.registerButton}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                :
                <p className = "font-semibold text-xl mt-8">{translations.residenceRegistration.guestMessage}</p>
            ) : null}
        </div>
    );
};

export default ResidenceRegistration;
