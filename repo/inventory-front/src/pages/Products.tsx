import React, { useState, useEffect } from 'react';
import {
  Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
  IconButton, Dialog, DialogActions, DialogContent, DialogTitle, TextField, FormControl, InputLabel,
  Select, MenuItem, TablePagination, Checkbox, FormControlLabel
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Swal from 'sweetalert2';
import { getProducts, createProduct, updateProduct, deleteProduct } from '../services/productService';
import styles from '../styles/home.module.css';
import { getCompanies } from '../services/companyService';
import { getCategories } from '../services/categoryService';
import { SelectChangeEvent } from '@mui/material';

const Products = () => {
  const [products, setProducts] = useState<any[]>([]);
  const [companies, setCompanies] = useState<any[]>([]);
  const [categories, setCategories] = useState<any[]>([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editProduct, setEditProduct] = useState<any>(null);
  const [form, setForm] = useState({
    code: '',
    name: '',
    description: '',
    price: '',
    companyNit: '',
    categoryIds: [] as string[] | any,
    isInInventory: false, // Nuevo estado para isInInventory
  });
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchProducts = async () => {
    try {
      const data = await getProducts();
      setProducts(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const fetchCompanies = async () => {
    try {
      const data = await getCompanies();
      setCompanies(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const fetchCategories = async () => {
    try {
      const data = await getCategories();
      setCategories(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    fetchProducts();
    fetchCompanies();
    fetchCategories();
  }, []);

  const handleOpenDialog = (product: any = null) => {
    setEditProduct(product);
    setForm(product ? {
      code: product.code,
      name: product.name,
      description: product.description,
      price: product.price,
      companyNit: product.company.nit,
      categoryIds: product.categories.map((cat: any) => cat.id) || [],
      isInInventory: product.isInInventory, // Asignar valor de isInInventory
    } : {
      code: '',
      name: '',
      description: '',
      price: '',
      companyNit: '',
      categoryIds: [],
      isInInventory: false,
    });
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, isInInventory: e.target.checked });
  };

  const handleCompanyChange = (event: SelectChangeEvent<string>) => {
    setForm({ ...form, companyNit: event.target.value });
  };

  const handleCategoryChange = (event: SelectChangeEvent<string[]>) => {
    setForm({ ...form, categoryIds: event.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (editProduct) {
        await updateProduct(form.code, form);
      } else {
        await createProduct(form);
      }
      fetchProducts();
      handleCloseDialog();
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const handleDelete = (code: string) => {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this product!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await deleteProduct(code);
          setProducts(products.filter((prod: any) => prod.code !== code));
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
        Products
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()} sx={{ mb: 2 }}>
        Add new product
      </Button>
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#ffffff' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#ffffff' }}>Code</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Name</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Description</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Price</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Company</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Categories</TableCell>
              <TableCell style={{ color: '#ffffff' }}>In Inventory</TableCell> {/* Nueva columna para isInInventory */}
              <TableCell style={{ color: '#ffffff' }}>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((product: any) => (
              product = { ...product, companyNit: product.company.nit },
              <TableRow key={product.code} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#ffffff' }}>{product.code}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.name}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.description}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.price}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.company.name}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>
                  {product.categories.map((category: any) => category.name).join(', ')}
                </TableCell>
                <TableCell style={{ color: '#ffffff' }}>
                  {product.isInInventory ? 'Yes' : 'No'} {/* Mostrar si está en inventario */}
                </TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenDialog(product)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(product.code)}>
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
          count={products.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#ffffff' }}
        />
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} PaperProps={{ style: { backgroundColor: '#2e2e2e', color: '#ffffff' } }}>
        <DialogTitle>{editProduct ? 'Edit Product' : 'Add New Product'}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Code"
            name="code"
            value={form.code}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
            sx={{
              '& .MuiInputBase-input.Mui-disabled': {
                WebkitTextFillColor: '#ccc',
              },
            }}
            disabled={editProduct ? true : false}
          />
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
          <TextField
            margin="dense"
            label="Description"
            name="description"
            value={form.description}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <TextField
            margin="dense"
            label="Price"
            name="price"
            type="number"
            value={form.price}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel
              id="company-select-label"
              style={{ color: '#ffffff' }}
            >
              Company
            </InputLabel>
            <Select
              labelId="company-select-label"
              id="company-select"
              value={form.companyNit}
              onChange={handleCompanyChange}
              style={{
                backgroundColor: '#333',
                color: '#ffffff',
                borderColor: '#555',
              }}
              inputProps={{
                style: { color: '#ffffff' },
              }}
              MenuProps={{
                PaperProps: {
                  style: {
                    backgroundColor: '#333',
                    color: '#ffffff',
                  },
                },
              }}
            >
              {companies.map((company: any) => (
                <MenuItem
                  key={company.nit}
                  value={company.nit}
                  style={{
                    backgroundColor: '#444',
                    color: '#ffffff',
                  }}
                >
                  {company.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl fullWidth margin="dense">
            <InputLabel
              id="category-select-label"
              style={{ color: '#ffffff' }}
            >
              Categories
            </InputLabel>
            <Select
              labelId="category-select-label"
              id="category-select"
              multiple
              value={form.categoryIds}
              onChange={handleCategoryChange}
              style={{
                backgroundColor: '#333',
                color: '#ffffff',
                borderColor: '#555',
              }}
              inputProps={{
                style: { color: '#ffffff' },
              }}
              MenuProps={{
                PaperProps: {
                  style: {
                    backgroundColor: '#333',
                    color: '#ffffff',
                  },
                },
              }}
            >
              {categories.map((category: any) => (
                <MenuItem
                  key={category.id}
                  value={category.id}
                  style={{
                    backgroundColor: '#444',
                    color: '#ffffff',
                  }}
                >
                  {category.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControlLabel
            control={
              <Checkbox
                checked={form.isInInventory}
                onChange={handleCheckboxChange}
                style={{ color: '#ffffff' }}
              />
            }
            label="Save in Inventory"
            style={{ color: '#ffffff' }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {editProduct ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default Products;
