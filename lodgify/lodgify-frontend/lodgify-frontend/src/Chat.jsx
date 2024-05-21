import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, Link } from "react-router-dom";
import { io } from 'socket.io-client';
import logo from "./assets/lodgify_logo.png";
import { useUser } from "./contexts/UserContext.jsx";
import { useLocale } from "./contexts/LocaleContext.jsx";
import en from "./translations/en.json";
import es from "./translations/es.json";
import lt from "./translations/lt.json";

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [recipient, setRecipient] = useState('');
  const { user, setUser } = useUser();
  const { locale, setLocale } = useLocale();
  const username = user.username;
  const socketRef = useRef();

  const translations = {
    en,
    es,
    lt,
  }[locale];

  useEffect(() => {
    socketRef.current = io('http://localhost:3000');

    socketRef.current.on('connect', () => {
      console.log('Connected with socket ID:', socketRef.current.id);
    });

    socketRef.current.on('chat message', (data) => {
      setMessages((prevMessages) => [
        ...prevMessages,
        { ...data, side: data.socketId === socketRef.current.id ? 'right' : 'left' },
      ]);
    });

    return () => {
      socketRef.current.disconnect();
    };
  }, []);

  const registerUser = () => {
    if (username.trim() === '') return;
    socketRef.current.emit('register', username);
  };

  const sendMessage = () => {
    if (message.trim() === '' || recipient.trim() === '') return;
    const data = { message, to: recipient };
    socketRef.current.emit('private message', data);
    setMessages((prevMessages) => [
      ...prevMessages,
      { ...data, socketId: socketRef.current.id, side: 'right' }
    ]);
    setMessage('');
  };

  return (
    <div className='flex flex-col items-center'>
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
      <div style={styles.body}>
        <div style={styles.chatContainer}>
          <div style={styles.messageContainer}>
            {messages.map((msg, index) => (
              <div
                key={index}
                style={{
                  ...styles.message,
                  ...(msg.side === 'left' ? styles.messageLeft : styles.messageRight),
                }}
              >
                {msg.message}
              </div>
            ))}
          </div>
          <div style={styles.inputContainer}>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Tu nombre de usuario"
              style={styles.input}
            />
            <button onClick={registerUser} style={styles.button}>Registrar</button>
          </div>
          <div style={styles.inputContainer}>
            <input
              type="text"
              value={recipient}
              onChange={(e) => setRecipient(e.target.value)}
              placeholder="Nombre del destinatario"
              style={styles.input}
            />
            <input
              type="text"
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
              placeholder="Escribe un mensaje..."
              style={styles.input}
            />
            <button onClick={sendMessage} style={styles.button}>Enviar</button>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  body: {
    fontFamily: 'Arial, sans-serif',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '85vh',
    margin: 0,
    backgroundColor: '#f0f0f0',
  },
  chatContainer: {
    width: '600px',
    height: '500px',
    backgroundColor: '#fff',
    borderRadius: '8px',
    boxShadow: '0 0 10px rgba(0,0,0,0.1)',
    display: 'flex',
    flexDirection: 'column',
  },
  messageContainer: {
    flex: 1,
    padding: '10px',
    overflowY: 'auto',
  },
  message: {
    margin: '5px 0',
    padding: '10px',
    borderRadius: '5px',
  },
  messageLeft: {
    backgroundColor: '#e0e0e0',
    textAlign: 'left',
    justifyContent: 'flex-start',
    alignSelf: 'flex-start',
    marginLeft: '20px',
    marginRight: 'auto',
    maxWidth: '60%',
    padding: '10px',
    borderRadius: '10px',
  },
  messageRight: {
    backgroundColor: '#007bff',
    color: '#fff',
    textAlign: 'right',
    alignSelf: 'flex-end',
    marginRight: '20px',
    marginLeft: 'auto',
    maxWidth: '60%',
    padding: '10px',
    borderRadius: '10px',
  },
  inputContainer: {
    display: 'flex',
    padding: '10px',
    borderTop: '1px solid #ddd',
  },
  input: {
    flex: 1,
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '5px',
  },
  button: {
    padding: '10px 15px',
    marginLeft: '10px',
    border: 'none',
    borderRadius: '5px',
    backgroundColor: '#007bff',
    color: '#fff',
    cursor: 'pointer',
  },
};

export default Chat;
