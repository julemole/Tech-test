import React, { useState } from 'react';
import { TextField, Button, Typography, Box } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import styles from '../styles/auth.module.css';

const LoginPage = () => {
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
      setError('Credenciales incorrectas o error en el servidor');
    }
  };

  return (
    <div className={styles.container}>
      <Box className={styles.formWrapper}>
        <Typography variant="h4" className={styles.title}>
          Welcome to Inventory App
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="Username"
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
              input: { color: '#fff' }, // Color del texto
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'white', // Color del borde cuando no está seleccionado
                },
                '&:hover fieldset': {
                  borderColor: 'white', // Color del borde al hacer hover
                },
              },
            }}
          />
          <TextField
            label="Password"
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
              input: { color: '#fff' }, // Color del texto
              '& .MuiOutlinedInput-root': {
                '& fieldset': {
                  borderColor: 'white', // Color del borde cuando no está seleccionado
                },
                '&:hover fieldset': {
                  borderColor: 'white', // Color del borde al hacer hover
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
            Login
          </Button>
          {error && (
            <Typography variant="body2" align="center" sx={{ mt: 2, color: 'red' }}>
              {error}
            </Typography>
          )}
          {/* <Typography variant="body2" align="center" sx={{ mt: 2 }}>
            <span>¿Dont have an account? </span>
            <a href="register" style={{ color: '#61dafb' }}>
              Register here
            </a>
          </Typography> */}
        </form>
      </Box>
    </div>
  );
};

export default LoginPage;
