import React, { useState, useEffect } from 'react';
import { Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Dialog, DialogActions, DialogContent, DialogTitle, TextField, TablePagination } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Swal from 'sweetalert2';
import { getCategories, createCategory, updateCategory, deleteCategory } from '../services/categoryService';
import styles from '../styles/home.module.css';

const Categories = () => {
  const [categories, setCategories] = useState<any[]>([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editCategory, setEditCategory] = useState<any>(null);
  const [form, setForm] = useState({ name: '' });
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleOpenDialog = (category: any = null) => {
    setEditCategory(category);
    setForm(category || { name: '' });
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
      if (editCategory) {
        await updateCategory(editCategory.id, form);
      } else {
        await createCategory(form);
      }
      fetchCategories();
      handleCloseDialog();
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const handleDelete = (id: string) => {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this category!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await deleteCategory(id);
          setCategories(categories.filter((cat: any) => cat.id !== id));
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
        Categories
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()} sx={{ mb: 2 }}>
        Create New Category
      </Button>
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#ffffff' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#ffffff' }}>Id</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Name</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {categories.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((category: any) => (
              <TableRow key={category.id} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#ffffff' }}>{category.id}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{category.name}</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenDialog(category)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(category.id)}>
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
          count={categories.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#ffffff' }}
        />
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} PaperProps={{ style: { backgroundColor: '#2e2e2e', color: '#ffffff' } }}>
        <DialogTitle>{editCategory ? 'Edit Category' : 'Add New Category'}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Name"
            name="name"
            value={form.name}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {editCategory ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Categories;