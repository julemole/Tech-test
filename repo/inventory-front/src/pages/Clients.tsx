import React, { useState, useEffect } from 'react';
import { Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Dialog, DialogActions, DialogContent, DialogTitle, TextField, TablePagination } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Swal from 'sweetalert2';
import { getClients, createClient, updateClient, deleteClient } from '../services/clientService';
import { useTranslation } from 'react-i18next';
import styles from '../styles/home.module.css';

const Clients: React.FC = () => {
  const { t } = useTranslation();
  const [clients, setClients] = useState<any[]>([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editClient, setEditClient] = useState<any>(null);
  const [form, setForm] = useState({ name: '', email: '', address: '', phone: '' });
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchClients = async () => {
    try {
      const data = await getClients();
      setClients(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    fetchClients();
  }, []);

  const handleOpenDialog = (client: any = null) => {
    setEditClient(client);
    setForm(client || { name: '', email: '', address: '', phone: '' });
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (editClient) {
        await updateClient(editClient.id, form);
      } else {
        await createClient(form);
      }
      fetchClients();
      handleCloseDialog();
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const handleDelete = (id: number) => {
    Swal.fire({
      title: t('areYouSure'),
      text: t('cannotRecoverClient'),
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: t('delete'),
      cancelButtonText: t('cancel'),
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await deleteClient(id);
          setClients(clients.filter((client: any) => client.id !== id));
        } catch (error: any) {
          console.error(error.message);
        }
      }
    });
  };

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div className={styles.mainContent}>
      <Typography variant="h4" gutterBottom style={{ color: '#ffffff' }}>
        {t('clients')}
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()} sx={{ mb: 2 }}>
        {t('addNewClient')}
      </Button>
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#ffffff' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#ffffff' }}>{t('id')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('name')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('email')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('address')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('phone')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {clients.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((client: any) => (
              <TableRow key={client.id} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#ffffff' }}>{client.id}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{client.name}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{client.email}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{client.address}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{client.phone}</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenDialog(client)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(client.id)}>
                    <DeleteIcon />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={clients.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#ffffff' }}
        />
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} PaperProps={{ style: { backgroundColor: '#2e2e2e', color: '#ffffff' } }}>
        <DialogTitle>{editClient ? t('editClient') : t('addNewClient')}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label={t('name')}
            name="name"
            value={form.name}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <TextField
            margin="dense"
            label={t('email')}
            name="email"
            value={form.email}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <TextField
            margin="dense"
            label={t('address')}
            name="address"
            value={form.address}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <TextField
            margin="dense"
            label={t('phone')}
            name="phone"
            value={form.phone}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="secondary">
            {t('cancel')}
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {editClient ? t('update') : t('create')}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Clients;