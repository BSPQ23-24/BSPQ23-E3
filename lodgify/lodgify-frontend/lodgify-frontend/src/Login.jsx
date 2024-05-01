import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import en from './translations/en.json';
import es from './translations/es.json';
import lt from './translations/lt.json';
import logo from "./assets/lodgify.png";
import spanishFlag from "./assets/spanishFlag.png";
import ukFlag from "./assets/ukFlag.png";
import lithuanianFlag from "./assets/lithuanianFlag.png";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from './contexts/LocaleContext.jsx';

const Login = () => {
  const [userType, setUserType] = useState("User");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { setUser } = useUser();
  const {locale, setLocale} = useLocale();

  const translations = {
    en,
    es,
    lt
  }[locale];

  const handleSubmit = async (e) => {
    e.preventDefault();
    const credentials = {
      username: username,
      password: password,
      user_type: userType
    };

    console.log(credentials);

    try {
      const response = await fetch("http://localhost:8080/rest/user/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credentials),
      });

      if (response.ok) {
        const userData = await response.json();
        console.log(userData.username);
        console.log(userData);
        setUser(userData);
        console.log("User logged in successfully!");
        navigate("/home");
      } else {
        console.log(username);
        console.error("Failed to log in user");
        setError(translations.login.errorMessages.invalidCredentials);
      }
    } catch (error) {
      console.error("Error fetching response from backend API:", error);
      setError(translations.login.errorMessages.serverError);
    }
  };

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="max-w-md w-full">
        <div className="flex justify-center">
          <img
              src={ukFlag}
              alt="UkFlag"
              className="w-10 h-10 cursor-pointer"
              onClick={() => setLocale('en')}
            />
            <img
              src={spanishFlag}
              alt="SpanishFlag"
              className="w-10 h-10 cursor-pointer"
              onClick={() => setLocale('es')}
            />
            <img
              src={lithuanianFlag}
              alt="LithuanianFlag"
              className="w-10 h-10 cursor-pointer"
              onClick={() => setLocale('lt')}
            />
        </div>
        <div className="bg-white p-8 rounded-lg shadow-md text-center mx-auto mt-4">
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
                <option value="User">{translations.login.userType.traveller}</option>
                <option value="Host">{translations.login.userType.host}</option>
              </select>
            </div>
            <div className="flex justify-center mt-4">
              <input
                type="text"
                placeholder={translations.login.placeholders.username}
                className="mb-2 p-2 rounded-md border"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
            </div>
            <div className="flex justify-center mt-4">
              <input
                type="password"
                placeholder={translations.login.placeholders.password}
                className="mb-2 p-2 rounded-md border"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            {error && <div className="text-red-500 mb-2">{error}</div>}
            <div className="flex mt-4 justify-center">
              <button
                type="submit"
                className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-md"
              >
                {translations.login.buttons.login}
              </button>
            </div>
          </form>
          <div className="flex mt-4 justify-center items-center">
            <p className="text-center">
              {translations.login.registerOption}
              <button
                className="font-bold text-blue-900"
                onClick={() => navigate("/register")}
              >
                {translations.login.buttons.registerNow}
              </button>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
