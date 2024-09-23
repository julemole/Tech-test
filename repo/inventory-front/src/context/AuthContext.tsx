/* eslint-disable react-hooks/exhaustive-deps */
import React, { createContext, useState, useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

interface AuthContextProps {
  user: string | null;
  token: string | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<string | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    const storedToken = localStorage.getItem('token');
    if (storedUser && storedToken) {
      setUser(storedUser);
      setToken(storedToken);
      navigate('/');
    }
  }, []);


  const login = async (username: string, password: string) => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API_URL}/auth/login`, {
        username,
        password,
      });
      const token = response.data.token;
      setUser(username);
      setToken(token);
      localStorage.setItem('user', username);
      localStorage.setItem('token', token);
      localStorage.setItem('role', response.data.role);
      navigate('/');
    } catch (error) {
      console.error('Error en la autenticación:', error);
      throw error;
    }
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <AuthContext.Provider value={{ user, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth debe ser usado dentro de un AuthProvider');
  }
  return context;
};
