import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from "./contexts/LocaleContext.jsx";
import logo from "./assets/lodgify_logo.png";
import apartamento from "./assets/apartamento.jpg";
import Confirmation from "./Confirmation.jsx";

const Profile = () => {
  const { user, setUser } = useUser();
  const [place, setPlace] = useState("Bilbao");
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
  const [error, setError] = useState("");
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [residenceIdToDelete, setResidenceIdToDelete] = useState(null);
  const [warning, setWarning] = useState("");
  const { locale, setLocale } = useLocale();

  const translations = {
    en,
    es,
    lt,
  }[locale];

  useEffect(() => {
    const handleSearch = async () => {
      try {
        console.log("Ha entrado");
        console.log(user.username);
        const response = await fetch(
          `http://localhost:8080/rest/residence/searchByUserID?user_id=${user.username}`
        );
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        console.log(data);
        setResidences(data);
      } catch (error) {
        console.error("Error:", error);
      }
    };

    console.log(user.id);
    handleSearch();
  }, [user.username]);

  const handleRemove = async () => {
    console.log("Removing residences");
    try {
      const response = await fetch(
        `http://localhost:8080/rest/residence/delete?residence_id=${residenceIdToDelete}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (response.ok) {
        setResidences(
          residences.filter((residence) => residence.id !== residenceIdToDelete)
        );
        console.log("Residence removed successfully.");
      } else {
        console.log("Failed to remove residence.");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

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
      const response = await fetch("http://localhost:8080/rest/user/modify", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
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
        setWarning(translations.profile.successMessage);
        setTimeout(() => {
          setWarning("");
        }, 5000);
        console.log("User data changed successfully!");
      } else {
        console.error(responseBody);
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleShowConfirmation = function (residenceId) {
    setShowConfirmation(true);
    setResidenceIdToDelete(residenceId);
  };

  const handleCancel = function () {
    setShowConfirmation(false);
  };

  return (
    <div className="text-center mx-auto">
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
              user.user_type === "Host" ? (
                <li>
                  <Link
                    to="/registerResidence"
                    style={{ color: "rgb(4, 18, 26)" }}
                    className="font-bold px-12"
                  >
                    {translations.home.residenceRegNav}
                  </Link>
                </li>
              ) : null
            ) : null}
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
      <h1 className="font-bold text-3xl mt-8 mb-8">
        {translations.profile.title}
      </h1>
      <div className="bg-gray-100 p-8 rounded-lg shadow-top text-center mx-auto w-2/5">
        <form onSubmit={handleSubmit}>
          <div className="mt-4 justify-center">
            <p className="font-bold text-md pb-2">
              {translations.placeholders.name}
            </p>
            <input
              type="text"
              placeholder="Name*"
              className="mb-2 p-2 rounded-md border"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className="mt-4 justify-center">
            <p className="font-bold text-md pb-2">
              {translations.placeholders.surname}
            </p>
            <input
              type="text"
              placeholder="Surname*"
              className="mb-2 p-2 rounded-md border"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
            />
          </div>
          <div className="mt-4 justify-center">
            <p className="font-bold text-md pb-2">
              {translations.placeholders.email}
            </p>
            <input
              type="text"
              placeholder="Email*"
              className="mb-2 p-2 rounded-md border"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className="mt-4 justify-center">
            <p className="font-bold text-md pb-2">
              {translations.placeholders.phoneNumber}
            </p>
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
                <p className="font-bold text-md pb-2">
                  {translations.placeholders.idCard}
                </p>
                <input
                  type="text"
                  placeholder="ID Card*"
                  className="mb-2 p-2 rounded-md border"
                  value={idCard}
                  onChange={(e) => setIdCard(e.target.value)}
                />
              </div>
              <div className="mt-4 justify-center">
                <p className="font-bold text-md pb-2">
                  {translations.placeholders.bankAccount}
                </p>
                <input
                  type="text"
                  placeholder="Bank account*"
                  className="mb-2 p-2 rounded-md border"
                  value={bankAccount}
                  onChange={(e) => setBankAccount(e.target.value)}
                />
              </div>
              <div className="mt-4 justify-center">
                <p className="font-bold text-md pb-2">
                  {translations.placeholders.socialSN}
                </p>
                <input
                  type="text"
                  placeholder="Social SN*"
                  className="mb-2 p-2 rounded-md border"
                  value={socialSN}
                  onChange={(e) => setSocialSN(e.target.value)}
                />
              </div>
              <div className="mt-4 justify-center">
                <p className="font-bold text-md pb-2">
                  {translations.placeholders.address}
                </p>
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
            <button
              onClick={handleSubmit}
              type="submit"
              className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md"
            >
              {translations.profile.buttonText}
            </button>
          </div>
        </form>
      </div>
      <div className="mt-4">
        <Link
          to="/passwordRecovery"
          className="text-blue-500 hover:text-blue-800"
        >
          {translations.profile.forgotPassword}
        </Link>
      </div>
      {user && user.user_type === "Host" ? (
        residences && residences.length > 0 ? (
          <div>
            <h1 className="font-bold text-3xl mt-8 mb-8">
              {translations.profile.yourResidences}
            </h1>
            {residences.map((residence) => (
              <div
                key={residence.id}
                className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center mx-auto w-4/5"
              >
                <img
                  src={apartamento}
                  alt="Apartamento"
                  className="mx-auto h-10 md:h-48 w-96 p-4 rounded-3xl"
                />
                <p className="p-4">
                  {translations.profile.location}: {residence.residence_address}
                </p>
                <p className="p-4">
                  {translations.profile.residenceType}:{" "}
                  {residence.residence_type}
                </p>
                <p className="p-4">
                  {translations.profile.price}: {residence.price}€
                </p>
                <div className="p-10">
                  <button onClick={() => handleShowConfirmation(residence.id)}>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth="1.5"
                      stroke="red"
                      className="w-6 h-6"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                      />
                    </svg>
                  </button>
                </div>
              </div>
            ))}
            {showConfirmation && (
              <Confirmation
                message={`Are you sure you want to delete this residence?`}
                onConfirm={handleRemove}
                onCancel={handleCancel}
              />
            )}
          </div>
        ) : (
          <h1 className="font-bold text-3xl mt-8 mb-8">
            {translations.profile.noResidences}
          </h1>
        )
      ) : null}
    </div>
  );
};

export default Profile;
