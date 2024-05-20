import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import logo from "./assets/lodgify_logo.png";
import apartamento from "./assets/apartamento.jpg";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from "./contexts/LocaleContext.jsx";

const HomePage = () => {
  const [place, setPlace] = useState("");
  const [start_date, setStartDate] = useState("");
  const [end_date, setEndDate] = useState("");
  const [residences, setResidences] = useState([]);
  const { user, setUser } = useUser();
  const [noResidencesFound, setNoResidencesFound] = useState(false);
  const { locale, setLocale } = useLocale();

  const navigate = useNavigate();

  console.log(locale);

  const translations = {
    en,
    es,
    lt,
  }[locale];

  const handleSearch = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/rest/residence/search?address=${place}`
      );
      console.log(response);
      if (response.status == 404) {
        setNoResidencesFound(true);
        return;
      } else if (!response.ok) {
        throw new Error(response);
      }
      const data = await response.json();
      setResidences(data);
      setNoResidencesFound(false);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleNavigation = (residence_id) => {
    navigate(`/reservation?residenceId=${residence_id}`);
  };

  const handleSearchBooking = async () => {
    try {
      const response = await fetch(`http://localhost:8080/rest/residence/book`);
      if (!response.ok) {
        throw new Error(response);
      }
      const data = await response.json();
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div className="flex flex-col items-center">
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
      <div className="bg-gray-100 m-20 mb-8 p-8 rounded-lg shadow-top text-center w-4/5">
        <h1 className="font-bold text-xl">{translations.home.title}</h1>
        <form onSubmit={handleSearch}>
          <div className="flex w-full justify-center">
            <input
              type="text"
              value={place}
              onChange={(e) => setPlace(e.target.value)}
              placeholder={translations.home.placePlaceholder}
              className="m-4 mt-8 p-2 w-72 rounded-md border"
            />
          </div>
          {/*
          <div className="flex w-full justify-center">
            <input
              type="text"
              value={start_date}
              onChange={(e) => setStartDate(e.target.value)}
              placeholder={translations.home.arrivalDatePlaceholder}
              className="m-4 p-2 w-56 rounded-md border"
            />
            <input
              type="text"
              value={end_date}
              onChange={(e) => setEndDate(e.target.value)}
              placeholder={translations.home.departureDatePlaceholder}
              className="m-4 p-2 w-56 rounded-md border"
            />
          </div>
          */}
        </form>
        <div className="flex mt-4 mx-auto w-4/5 justify-center">
          <button
            onClick={handleSearch}
            className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-xl"
          >
            {translations.home.searchButton}
          </button>
        </div>
      </div>

      <div>
        {residences.map((residence) => (
          <div
            key={residence.id}
            className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center w-4/5"
          >
            <img
              src={apartamento}
              alt="Apartamento"
              className="mx-auto h-10 md:h-48 w-96 p-4 rounded-3xl"
            />
            <p className="p-4">
              {translations.home.locationLabel}: {residence.residence_address}
            </p>
            <p className="p-4">
              {translations.home.residenceTypeLabel}: {residence.residence_type}
            </p>
            <p className="p-4">
              {translations.home.priceLabel}: {residence.price}€
            </p>
            <button
              className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-xl"
              onClick={() => handleNavigation(residence.id)}
            >
              {translations.home.bookButton}
            </button>
            <button
              className="font-bold text-blue-900"
              onClick={handleClick}
            >
              <div className="p-4">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M10.5 1.5H8.25A2.25 2.25 0 0 0 6 3.75v16.5a2.25 2.25 0 0 0 2.25 2.25h7.5A2.25 2.25 0 0 0 18 20.25V3.75a2.25 2.25 0 0 0-2.25-2.25H13.5m-3 0V3h3V1.5m-3 0h3m-3 18.75h3" />
                </svg>
              </div>
            </button>
          </div>
        ))}
      </div>
      {noResidencesFound && (
        <h1 className="font-bold text-xl mb-20 underline">
          {translations.home.noResidencesFound}
        </h1>
      )}

      <footer>
        <p>&copy; {translations.footer.copyright}</p>
      </footer>
    </div>
  );
};

export default HomePage;
