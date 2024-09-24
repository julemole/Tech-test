import React, { useState } from 'react';
import { TextField, Button, Typography, Box } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { useTranslation } from 'react-i18next';
import styles from '../styles/auth.module.css';

const LoginPage: React.FC = () => {
  const { t } = useTranslation();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth(); // Traemos el método login desde el contexto
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // Llamamos al método login con las credenciales
      await login(username, password);
    } catch (err) {
      setError(t('loginError'));
    }
  };

  return (
    <div className={styles.container}>
      <Box className={styles.formWrapper}>
        <Typography variant="h4" className={styles.title}>
          {t('welcome')}
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label={t('username')}
            variant="outlined"
            fullWidth
            margin="normal"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            InputProps={{
              style: { color: 'white', borderColor: 'red' },
            }}
            InputLabelProps={{
              style: { color: 'white' },
            }}
            sx={{
              input: { color: '#fff' },
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'white',
                },
                '&:hover fieldset': {
                  borderColor: 'white',
                },
              },
            }}
          />
          <TextField
            label={t('password')}
            type="password"
            variant="outlined"
            fullWidth
            margin="normal"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            InputProps={{
              style: { color: 'white' },
            }}
            InputLabelProps={{
              style: { color: 'white' },
            }}
            sx={{
              input: { color: '#fff' },
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'white',
                },
                '&:hover fieldset': {
                  borderColor: 'white',
                },
              },
            }}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 2 }}
          >
            {t('login')}
          </Button>
          {error && (
            <Typography variant="body2" align="center" sx={{ mt: 2, color: 'red' }}>
              {error}
            </Typography>
          )}
          {/* <Typography variant="body2" align="center" sx={{ mt: 2 }}>
            <span>{t('noAccount')}</span>
            <a href="register" style={{ color: '#61dafb' }}>
              {t('registerHere')}
            </a>
          </Typography> */}
        </form>
      </Box>
    </div>
  );
};

export default LoginPage;