import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Companies from './pages/Companies';
import PrivateRoute from './components/PrivateRoute';
import Products from './pages/Products';
import Categories from './pages/Categories';
import Clients from './pages/Clients';
import CustomerOrders from './pages/CustomerOrder';
import Inventory from './pages/Inventory';

function App() {
  return (
    <Routes>
      <Route element={<PrivateRoute />}>
        <Route path="/" element={<Dashboard />}>
          <Route path="companies" element={<Companies />} />
          <Route path="products" element={<Products />} />
          <Route path="categories" element={<Categories />} />
          <Route path="clients" element={<Clients />} />
          <Route path="orders" element={<CustomerOrders />} />
          <Route path="inventory" element={<Inventory />} />
        </Route>
      </Route>
      <Route path="/login" element={<Login />} />
    </Routes>
  );
}

export default App;
