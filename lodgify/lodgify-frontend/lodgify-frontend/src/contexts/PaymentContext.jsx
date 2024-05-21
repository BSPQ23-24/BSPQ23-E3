import React, { createContext, useState } from 'react';

const PaymentContext = createContext();

export const usePayment = () => useContext(PaymentContext);

export const PaymentProvider = ({ children }) => {
    const [isPaid, setIsPaid] = useState(false);

    return (
        <PaymentContext.Provider value={{ isPaid, setIsPaid }}>
            {children}
        </PaymentContext.Provider>
    );
};