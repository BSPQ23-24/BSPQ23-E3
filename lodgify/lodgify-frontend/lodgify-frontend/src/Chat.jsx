import React, { useState, useEffect, useRef } from 'react';
import { useUser } from "./contexts/UserContext.jsx";
import { io } from 'socket.io-client';

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [recipient, setRecipient] = useState('');
  const { user, setUser } = useUser();
  const username = user.username;
  const socketRef = useRef();

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
  );
};

const styles = {
  body: {
    fontFamily: 'Arial, sans-serif',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100vh',
    margin: 0,
    backgroundColor: '#f0f0f0',
  },
  chatContainer: {
    width: '600px',
    height: '600px',
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
    alignSelf: 'flex-start',
  },
  messageRight: {
    backgroundColor: '#007bff',
    color: '#fff',
    textAlign: 'right',
    alignSelf: 'flex-end',
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