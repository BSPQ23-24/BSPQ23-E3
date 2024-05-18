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

function Bookings() {
  const [bookings, setBookings] = useState([]);
  const [residences, setResidences] = useState({});
  const [headerText, setHeaderText] = useState("");
  const [refreshNeeded, setRefreshNeeded] = useState(false);
  const [showConfirmation, setShowConfirmation] = useState(false);
  const [bookingIdToDelete, setBookingIdToDelete] = useState(null);
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

  const removeBooking = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/rest/booking/deleteBooking?booking_id=${bookingIdToDelete}`,
        {
          method: "POST",
        }
      );
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data);
      } else {
        setBookings((bookings) =>
          bookings.filter((booking) => booking.id !== bookingIdToDelete)
        );
        setRefreshNeeded((prev) => !prev); // refresh header
        setShowConfirmation(false); // Close the confirmation dialog
      }
    } catch (error) {
      console.error("Trouble removing booking: ", error);
    }
  };

  const handleCancel = () => {
    setShowConfirmation(false);
    setBookingIdToDelete(null);
  };

  const handleShowConfirmation = (bookingId) => {
    setShowConfirmation(true);
    setBookingIdToDelete(bookingId);
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
            <div className="p-10">
              {/* <button onClick={(e) => removeBooking(booking.id)}> */}
              <button onClick={() => handleShowConfirmation(booking.id)}>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="red"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
                  />
                </svg>
              </button>
            </div>
            {showConfirmation && (
              <Confirmation
                message={`Are you sure you want to delete this booking?`}
                onConfirm={removeBooking}
                onCancel={handleCancel}
              />
            )}
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
