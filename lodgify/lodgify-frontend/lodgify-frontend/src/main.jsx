// src/main.js

import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { UserProvider } from './contexts/UserContext'; 
import { LocaleProvider } from './contexts/LocaleContext'; 
import './index.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <LocaleProvider>
    <UserProvider>
      <App />
    </UserProvider>
    </LocaleProvider>
  </React.StrictMode>,
);
