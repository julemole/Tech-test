import React, { useState, useEffect } from 'react';
import { Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Dialog, DialogActions, DialogContent, DialogTitle, TextField, TablePagination } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { getCompanies, createCompany, updateCompany, deleteCompany } from '../services/companyService';
import { useTranslation } from 'react-i18next';
import styles from '../styles/home.module.css';
import Swal from 'sweetalert2';

const Companies: React.FC = () => {
  const { t } = useTranslation();
  const [role, setRole] = useState('');
  const [companies, setCompanies] = useState<any[]>([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editCompany, setEditCompany] = useState<any>(null);
  const [form, setForm] = useState({ nit: '', name: '', address: '', phone: '' });
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchCompanies = async () => {
    try {
      const data = await getCompanies();
      setCompanies(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    const role = localStorage.getItem('role');
    if (role) setRole(role);
    fetchCompanies();
  }, []);

  const handleOpenDialog = (company: any = null) => {
    setEditCompany(company);
    setForm(company || { nit: '', name: '', address: '', phone: '' });
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
      if (editCompany) {
        await updateCompany(form.nit, form);
      } else {
        await createCompany(form);
      }
      fetchCompanies();
      handleCloseDialog();
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const handleDelete = (nit: string) => {
    Swal.fire({
      title: t('areYouSure'),
      text: t('cannotRecoverCompany'),
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: t('delete'),
      cancelButtonText: t('cancel'),
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await deleteCompany(nit);
          setCompanies(companies.filter((comp: any) => comp.nit !== nit));
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
      <Typography variant="h4" gutterBottom style={{ color: '#fff' }}>
        {t('companies')}
      </Typography>
      {role === 'ROLE_ADMINISTRATOR' &&
      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()} sx={{ mb: 2 }}>
        {t('addNewCompany')}
      </Button>}
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#fff' }}>
        <Table size='small'>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#fff' }}>{t('nit')}</TableCell>
              <TableCell style={{ color: '#fff' }}>{t('name')}</TableCell>
              <TableCell style={{ color: '#fff' }}>{t('address')}</TableCell>
              <TableCell style={{ color: '#fff' }}>{t('phone')}</TableCell>
              <TableCell style={{ color: '#fff' }}>{t('actions')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {companies.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((company: any) => (
              <TableRow key={company.nit} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#fff' }}>{company.nit}</TableCell>
                <TableCell style={{ color: '#fff' }}>{company.name}</TableCell>
                <TableCell style={{ color: '#fff' }}>{company.address}</TableCell>
                <TableCell style={{ color: '#fff' }}>{company.phone}</TableCell>
                <TableCell>
                  {role === 'ROLE_ADMINISTRATOR' && (
                    <>
                      <IconButton color="primary" onClick={() => handleOpenDialog(company)}>
                        <EditIcon />
                      </IconButton>
                      <IconButton color="secondary" onClick={() => handleDelete(company.nit)}>
                        <DeleteIcon />
                      </IconButton>
                    </>
                  )}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={companies.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#fff' }}
        />
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} PaperProps={{ style: { backgroundColor: '#2e2e2e', color: '#fff' } }}>
        <DialogTitle>{editCompany ? t('editCompany') : t('createNewCompany')}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label={t('nit')}
            name="nit"
            type="number"
            value={form.nit}
            onChange={handleChange}
            fullWidth
            InputProps={{
              style: { color: '#ffffff' },
            }}
            InputLabelProps={{
              style: { color: '#ffffff' },
            }}
            sx={{
              '& .MuiInputBase-input.Mui-disabled': {
                WebkitTextFillColor: '#ccc',
              },
            }}
            disabled={editCompany ? true : false}
          />
          <TextField
            margin="dense"
            label={t('name')}
            name="name"
            value={form.name}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#fff' } }}
            InputLabelProps={{ style: { color: '#fff' } }}
          />
          <TextField
            margin="dense"
            label={t('address')}
            name="address"
            value={form.address}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#fff' } }}
            InputLabelProps={{ style: { color: '#fff' } }}
          />
          <TextField
            margin="dense"
            label={t('phone')}
            name="phone"
            type="number"
            value={form.phone}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#fff' } }}
            InputLabelProps={{ style: { color: '#fff' } }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="secondary" variant='outlined'>
            {t('cancel')}
          </Button>
          <Button onClick={handleSubmit} color="primary" variant='outlined'>
            {editCompany ? t('update') : t('create')}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Companies;