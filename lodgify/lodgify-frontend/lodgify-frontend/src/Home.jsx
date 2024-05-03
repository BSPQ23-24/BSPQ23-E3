import React, { useState } from "react";
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
  const { locale, setLocale } = useLocale();
  const navigate = useNavigate();

  console.log(locale);

  const translations = locale
    ? {
        en,
        es,
        lt,
      }[locale]
    : en;

  const handleSearch = async () => {
    console.log("hadnling search");
    try {
      const response = await fetch(
        `http://localhost:8080/rest/residence/search?address=${place}&start_date=${start_date}&end_date=${end_date}`
      );
      if (response.status == 404) {
        document.getElementById("noResidencesFound").innerText =
          translations.home.noResidencesFound;
      } else if (!response.ok) {
        throw new Error(response);
      } else {
        const data = await response.json();
        setResidences(data);
        document.getElementById("noResidencesFound").innerText = "";
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  const handleNavigation = (residence_id) => {
    console.log("navigating ????");
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
            <li>
              <Link
                to="/registerResidence"
                style={{ color: "rgb(4, 18, 26)" }}
                className="font-bold px-12"
              >
                {translations.home.residenceRegNav}
              </Link>
            </li>
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
        <form
          onSubmit={(e) => {
            e.preventDefault(); // Prevent default form submission behavior
            handleSearch(); // Call your search handler
          }}
        >
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
            onClick={() => handleSearch()}
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
            className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center w-5/5"
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
              onClick={() => handleNavigation(residence.id)}
              className="bg-blue-950 hover:bg-blue-500 text-white py-2 px-4 rounded-xl"
            >
              {translations.home.bookButton}
            </button>
          </div>
        ))}
        <h1
          id="noResidencesFound"
          className="font-bold text-3xl mt-8 mb-8"
        ></h1>
      </div>

      <footer>
        <p>&copy; {translations.footer.copyright}</p>
      </footer>
    </div>
  );
};

export default HomePage;
