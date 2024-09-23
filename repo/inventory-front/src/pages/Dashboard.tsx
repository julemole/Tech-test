import React from 'react';
import { Outlet, NavLink } from 'react-router-dom';
import Header from '../components/Header';
import SideNav from '../components/Sidenav';

const Dashboard = () => {
  return (
    <div className="dashboard-layout">
      <Header />
      <div className="dashboard-content">
        <SideNav />
        <div className="dashboard-page">
          {/* Aquí se renderizarán las subrutas */}
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
