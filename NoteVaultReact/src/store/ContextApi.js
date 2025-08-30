import React, { createContext, useContext, useState, useEffect } from "react";
import api from "../services/api";
import toast from "react-hot-toast";

const ContextApi = createContext();

export const ContextProvider = ({ children }) => {
  // Load token from localStorage
  const initialToken = localStorage.getItem("JWT_TOKEN") || null;

  const [token, setToken] = useState(initialToken);
  const [currentUser, setCurrentUser] = useState(null);
  const [openSidebar, setOpenSidebar] = useState(true);
  const [isAdmin, setIsAdmin] = useState(false);

  const fetchUser = async () => {
    if (!token) return;

    try {
      const { data } = await api.get("/auth/user"); // JWT auto-attached by api.js
      setCurrentUser(data);

      if (data.roles?.includes("ROLE_ADMIN")) {
        setIsAdmin(true);
        localStorage.setItem("IS_ADMIN", "true");
      } else {
        setIsAdmin(false);
        localStorage.removeItem("IS_ADMIN");
      }

      // Optionally cache user in localStorage
      localStorage.setItem("USER", JSON.stringify(data));
    } catch (error) {
      console.error("Error fetching current user", error);
      toast.error("Error fetching current user");

      // Token may be expired → clear state
      handleLogout();
    }
  };

  useEffect(() => {
    if (token) {
      fetchUser();
    }
  }, [token]);

  const handleLogin = (jwt, userData) => {
    localStorage.setItem("JWT_TOKEN", jwt);
    localStorage.setItem("USER", JSON.stringify(userData));
    setToken(jwt);
    setCurrentUser(userData);

    if (userData.roles?.includes("ROLE_ADMIN")) {
      setIsAdmin(true);
      localStorage.setItem("IS_ADMIN", "true");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("JWT_TOKEN");
    localStorage.removeItem("USER");
    localStorage.removeItem("IS_ADMIN");
    setToken(null);
    setCurrentUser(null);
    setIsAdmin(false);
  };

  return (
    <ContextApi.Provider
      value={{
        token,
        setToken,
        currentUser,
        setCurrentUser,
        openSidebar,
        setOpenSidebar,
        isAdmin,
        setIsAdmin,
        handleLogin,
        handleLogout,
      }}
    >
      {children}
    </ContextApi.Provider>
  );
};

export const useMyContext = () => useContext(ContextApi);
