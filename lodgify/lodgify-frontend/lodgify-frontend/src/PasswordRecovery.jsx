import React, { useState } from "react";
import { Link } from "react-router-dom";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from "./contexts/LocaleContext.jsx";
import logo from "./assets/lodgify_logo.png";

const PasswordRecoveryPage = () => {
  const { user } = useUser();
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const { locale, setLocale } = useLocale();

  const translations = {
    en,
    es,
    lt,
  }[locale];

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Email validation
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      setError("Please enter a valid email address.");
      return;
    }

    // Simulate a successful password recovery
    setSuccess("Password recovery email sent successfully!");
  };

  return (
    <div className="text-center">
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
                to="/profile"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.home.profileNav}
              </Link>
            </li>
            <li>
              <Link
                to="/login"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.logout}
              </Link>
            </li>
          </ul>
        </div>
      </nav>
      <h1 className="font-bold text-3xl mt-8">Password Recovery</h1>
      <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
        <form onSubmit={handleSubmit}>
          <div className="flex mt-4 justify-center">
            <input
              type="email"
              placeholder="Email address"
              className="mb-2 p-2 rounded-md border"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          {error && <div className="text-red-500 mb-2">{error}</div>}
          {success && <div className="text-green-500 mb-2">{success}</div>}
          <div className="flex mt-4 justify-center">
            <button
              type="submit"
              className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md"
            >
              Recover Password
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default PasswordRecoveryPage;
