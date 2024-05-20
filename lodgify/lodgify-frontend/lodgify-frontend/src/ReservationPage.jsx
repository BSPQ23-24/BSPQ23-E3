import React, { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import logo from "./assets/lodgify_logo.png";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import { useUser } from "./contexts/UserContext";
import { useLocale } from "./contexts/LocaleContext.jsx";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const ReservationPage = () => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const { locale } = useLocale();
  const [residence, setResidence] = useState(null);

  const { user } = useUser();
  const query = useQuery();
  const residenceId = query.get("residenceId");

  const translations = {
    en,
    es,
    lt,
  }[locale];

  useEffect(() => {
    // Fetch residence data
    fetch(
      `http://localhost:8080/rest/residence/searchByResidenceID?residence_id=${residenceId}`
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch residence data");
        }
        return response.json();
      })
      .then((residenceData) => {
        setResidence(residenceData);
      })
      .catch((error) => {
        console.error("Error fetching residence data:", error);
      });
  }, [residenceId]);

  const checkAvailability = async () => {
    const bookingData = {
      startDate,
      endDate,
      residenceId: residence.id,
    };

    console.log(bookingData)
    try {
      const response = await fetch("http://localhost:8080/rest/booking/checkAvailability", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(bookingData),
      });

      if (response.ok) {
        return true;
      } else {
        console.log(response)
        const message = await response.text();
        setError(message);
        setTimeout(() => {
          setError("");
        }, 5000);
        return false;
      }
    } catch (error) {
      console.error("Error:", error);
      setError("An error occurred while checking availability.");
      return false;
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!user || !residence) {
      setError("Failed to fetch user or residence data");
      return;
    }

    if (!await checkAvailability()) {
      return;
    }

    const bookingData = {
      startDate,
      endDate,
      travelerUsername: user.username,
      hostUsername: residence.user_username,
      residenceId: residence.id,
    };

    try {
      const response = await fetch("http://localhost:8080/rest/booking/save", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(bookingData),
      });

      if (!response.ok) {
        throw new Error("Failed to book residence");
      }

      // Handle success
      setStartDate(new Date());
      setEndDate(new Date());
      setSuccess("Residence booked successfully!");
      setTimeout(() => {
        setSuccess("");
      }, 5000);
    } catch (error) {
      // Handle error
      setError("Failed to book residence");
      console.error("Error:", error);
    }
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
      <h1 className="font-bold text-3xl mt-8">Reservation Process</h1>
      <div className="bg-gray-100 m-8 mb-8 mx-auto p-8 rounded-lg shadow-top text-center w-2/5">
        <form onSubmit={handleSubmit}>
          <div className="flex flex-col mt-4 items-center">
            <p className="font-bold text-md pb-2">Arrival date</p>
            <DatePicker
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              startDate={startDate}
              endDate={endDate}
              selectsStart
              className="mt-2"
            />
            <p className="font-bold text-md mt-2 pb-2">Departure date</p>
            <DatePicker
              selected={endDate}
              onChange={(date) => setEndDate(date)}
              startDate={startDate}
              endDate={endDate}
              selectsEnd
              minDate={startDate}
              className="mt-2"
            />
          </div>
          <div className="flex mt-4 justify-center">
            <button
              type="submit"
              className="bg-blue-950 hover:bg-blue-950 text-white py-2 px-4 rounded-md"
            >
              Book Residence
            </button>
          </div>
          {error && <div className="text-red-500 m-2">{error}</div>}
          {success && <div className="text-green-500 m-2">{success}</div>}
        </form>
      </div>
    </div>
  );
};

export default ReservationPage;
