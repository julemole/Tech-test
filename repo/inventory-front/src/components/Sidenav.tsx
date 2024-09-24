import React, { useEffect, useState } from 'react';
import { List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import BusinessIcon from '@mui/icons-material/Business';
import InventoryIcon from '@mui/icons-material/Inventory';
import CategoryIcon from '@mui/icons-material/Category';
import ListAltIcon from '@mui/icons-material/ListAlt';
import PeopleIcon from '@mui/icons-material/People';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { NavLink } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import styles from '../styles/home.module.css';

const SideNav: React.FC = () => {
  const { t } = useTranslation();
  const [role, setRole] = useState('');

  useEffect(() => {
    const role = localStorage.getItem('role');
    if (role) setRole(role);
  }, []);

  const menuItems = [
    { text: t('companies'), icon: <BusinessIcon />, path: '/companies' },
    { text: t('categories'), icon: <CategoryIcon />, path: '/categories' },
    { text: t('products'), icon: <ShoppingCartIcon />, path: '/products' },
    { text: t('clients'), icon: <PeopleIcon />, path: '/clients' },
    { text: t('orders'), icon: <ListAltIcon />, path: '/orders' },
    { text: t('inventory'), icon: <InventoryIcon />, path: '/inventory' },
  ];

  const filteredMenuItems = role === 'ROLE_ADMINISTRATOR' ? menuItems : menuItems.filter(item => item.text === t('companies'));

  return (
    <div className={styles.sidenav}>
      <List>
        {filteredMenuItems.map((item) => (
          <NavLink
            style={{ display: 'flex' }}
            key={item.text}
            to={item.path}
            className={({ isActive }) => isActive ? `${styles.menuItem} ${styles.selected}` : styles.menuItem}
          >
            <ListItem>
              <ListItemIcon>{React.cloneElement(item.icon, { style: { color: 'white' } })}</ListItemIcon>
              <ListItemText primary={item.text} style={{ color: 'white' }} />
            </ListItem>
          </NavLink>
        ))}
      </List>
    </div>
  );
};

export default SideNav;