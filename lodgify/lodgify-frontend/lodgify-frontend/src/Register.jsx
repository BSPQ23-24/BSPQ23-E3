// Register.jsx
import React, { useState } from "react";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import logo from "./assets/lodgify.png";
import { useNavigate } from "react-router-dom";
import { useLocale } from "./contexts/LocaleContext.jsx";

const Register = ({ showLoginForm }) => {
  // Recibe la función showLoginForm como prop
  const [userType, setUserType] = useState("User");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [idCard, setIdCard] = useState("");
  const [bankAccount, setBankAccount] = useState(0);
  const [socialSN, setSocialSN] = useState(0);
  const [address, setAddress] = useState("");
  const [error, setError] = useState("");
  const [warning, setWarning] = useState("");
  const navigate = useNavigate();
  const { locale, setLocale } = useLocale();

  const translations = {
    en,
    es,
    lt,
  }[locale];

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
      const response = await fetch("http://localhost:8080/rest/user/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });
      const responseBody = await response.text();
      if (response.ok) {
        setError("");
        setName("");
        setSurname("");
        setUsername("");
        setPassword("");
        setEmail("");
        setPhoneNumber("");
        setUserType("User");
        setIdCard("");
        setBankAccount("");
        setSocialSN("");
        setAddress("");
        setWarning(translations.register.successMessage);
        console.log("User registered successfully!");
      } else if (responseBody == "User already exists!") {
        console.error(
          "Failed to register user because the user already exists"
        );
        setError(translations.register.errors.userExists);
      } else if (responseBody == "Fill all the data!") {
        console.error(
          "Failed to register user because all the data has not been filled"
        );
        setError(translations.register.errors.fillData);
      } else if (responseBody == "Not valid phone number!") {
        console.error(
          "Failed to register user because the phone number must have 9 digits"
        );
        setError(translations.register.errors.invalidPhoneNumber);
      } else if (responseBody == "Not valid email!") {
        console.error("Failed to register user because of email");
        setError(translations.register.errors.invalidEmail);
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
        <div className="flex justify-center mb-6">
          <img src={logo} alt="Logo" className="h-10 md:h-48" />
        </div>
        <form onSubmit={handleSubmit}>
          <div className="flex mt-4 justify-center">
            <select
              className="mb-2 p-2 rounded-md border"
              value={userType}
              onChange={(e) => setUserType(e.target.value)}
            >
              <option value="User">
                {translations.register.userType.traveller}
              </option>
              <option value="Host">
                {translations.register.userType.host}
              </option>
            </select>
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="text"
              placeholder={translations.placeholders.name + "*"}
              className="mb-2 p-2 rounded-md border"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="text"
              placeholder={translations.placeholders.surname + "*"}
              className="mb-2 p-2 rounded-md border"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
            />
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="text"
              placeholder={translations.placeholders.username + "*"}
              className="mb-2 p-2 rounded-md border"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="password"
              placeholder={translations.placeholders.password + "*"}
              className="mb-2 p-2 rounded-md border"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="text"
              placeholder={translations.placeholders.email + "*"}
              className="mb-2 p-2 rounded-md border"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className="flex mt-4 justify-center">
            <input
              type="number"
              placeholder={translations.placeholders.phoneNumber + "*"}
              className="mb-2 p-2 rounded-md border"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
            />
          </div>
          {userType === "Host" && (
            <>
              <div className="flex mt-4 justify-center">
                <input
                  type="text"
                  placeholder={translations.placeholders.idCard + "*"}
                  className="mb-2 p-2 rounded-md border"
                  value={idCard}
                  onChange={(e) => setIdCard(e.target.value)}
                />
              </div>
              <div className="flex mt-4 justify-center">
                <input
                  type="text"
                  placeholder={translations.placeholders.bankAccount + "*"}
                  className="mb-2 p-2 rounded-md border"
                  value={bankAccount}
                  onChange={(e) => setBankAccount(e.target.value)}
                />
              </div>
              <div className="flex mt-4 justify-center">
                <input
                  type="text"
                  placeholder={translations.placeholders.socialSN + "*"}
                  className="mb-2 p-2 rounded-md border"
                  value={socialSN}
                  onChange={(e) => setSocialSN(e.target.value)}
                />
              </div>
              <div className="flex mt-4 justify-center">
                <input
                  type="text"
                  placeholder={translations.placeholders.address + "*"}
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
              type="submit"
              className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md"
            >
              {translations.register.buttonText}
            </button>
          </div>
        </form>
        <div className="flex mt-4">
          {/* Llama a la función handleBack al hacer clic */}
          <button
            className="font-bold text-blue-950 justify-center text-center"
            onClick={() => navigate("/")}
          >
            {"<"}
            {translations.register.backButtonText}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Register;
