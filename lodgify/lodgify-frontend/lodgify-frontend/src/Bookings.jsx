import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from "./contexts/LocaleContext.jsx";
import logo from "./assets/lodgify_logo.png";
import apartamento from "./assets/apartamento.jpg";

function Bookings() {
  const [bookings, setBookings] = useState([]);
  const [residences, setResidences] = useState({});
  const [headerText, setHeaderText] = useState("");
  const [refreshNeeded, setRefreshNeeded] = useState(false);
  const { user, setUser } = useUser();
  const { locale, setLocale } = useLocale();

  const translations = {
    en,
    es,
    lt,
  }[locale];

  useEffect(() => {
    const getBookings = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/rest/booking/searchByUserUsername?user_username=${user.username}`
        );

        if (response.status == 404) {
          document.getElementById("areThereBookingsHeader").innerText =
            translations.profile.noBookings;
        } else if (!response.ok) {
          const data = await response.text();
          throw new Error(data);
        } else {
          const data = await response.json();
          setBookings(data);
          getResidences(data); // gets residences
          console.log(data);
          setHeaderText(
            data.length > 0
              ? translations.profile.yourBookings
              : translations.profile.noBookings
          );
        }
      } catch (error) {
        console.error("Error:", error);
      }
    };
    const getResidences = async (bookings) => {
      const residencePromises = bookings.map(async (booking) => {
        const response = await fetch(
          `http://localhost:8080/rest/residence/searchByResidenceID?residence_id=${booking.residenceId}`
        );
        const data = await response.json();
        return { id: booking.residenceId, data };
      });

      const residencesData = await Promise.all(residencePromises);
      setResidences(
        residencesData.reduce((acc, residence) => {
          acc[residence.id] = residence.data;
          return acc;
        }, {})
      );
    };
    getBookings(); // this also gets residences
  }, [user.username, refreshNeeded]);

  const removeBooking = async (bookingId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/rest/booking/deleteBooking?booking_id=${bookingId}`,
        {
          method: "POST",
        }
      );
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data);
      } else {
        // Remove the div by filtering out the booking with the specific ID
        setBookings((bookings) =>
          bookings.filter((booking) => booking.id !== bookingId)
        );
        setRefreshNeeded((prev) => !prev); // refresh header
      }
    } catch (error) {
      console.error("Trouble removing booking: ", error);
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
          </ul>
        </div>
      </nav>
      <h1 id="areThereBookingsHeader" className="font-bold text-3xl mt-8 mb-8">
        {headerText}
      </h1>
      <div>
        {bookings.map((booking) => (
          <div
            key={booking.id}
            className="flex bg-gray-100 m-4 mb-8 p-8 rounded-lg mx-auto shadow-top items-center text-center w-full"
          >
            <img
              src={apartamento} // ensure this variable is defined or imported
              alt="Apartamento"
              className="mx-auto h-10 md:h-48 w-96 p-4 rounded-3xl"
            />
            <p className="p-4">
              {translations.profile.location}:{" "}
              {residences[booking.residenceId]?.residence_address ||
                "Loading..."}
            </p>
            <p className="p-4">
              {translations.profile.residenceType}:{" "}
              {residences[booking.residenceId]?.residence_type || "Loading..."}
            </p>
            <p className="p-4">
              {translations.profile.price}:{" "}
              {residences[booking.residenceId]?.price
                ? `${residences[booking.residenceId].price}€`
                : "Loading..."}
            </p>
            <p className="p-4">
              {translations.login.userType.host}: {booking.hostUsername}
            </p>
            <span
              className="text-red-500 hover:text-red-700 font-bold cursor-pointer px-2 py-1 text-xl"
              onClick={() => removeBooking(booking.id)}
            >
              X
            </span>
          </div>
        ))}
      </div>

      <footer>
        <p>&copy; {translations.footer.copyright}</p>
      </footer>
    </div>
  );
}

export default Bookings;
