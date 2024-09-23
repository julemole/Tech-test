import React, { useState, useEffect } from 'react';
import { Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Dialog, DialogActions, DialogContent, DialogTitle, TextField, FormControl, InputLabel, Select, MenuItem, TablePagination } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import Swal from 'sweetalert2';
import { getCustomerOrders, createCustomerOrder, updateCustomerOrder, deleteCustomerOrder } from '../services/customerOrderService';
import { getClients } from '../services/clientService';
import { getProducts } from '../services/productService';
import styles from '../styles/home.module.css';
import { SelectChangeEvent } from '@mui/material';

const CustomerOrders = () => {
  const [orders, setOrders] = useState<any[]>([]);
  const [clients, setClients] = useState<any[]>([]);
  const [products, setProducts] = useState<any[]>([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [editOrder, setEditOrder] = useState<any>(null);
  const [form, setForm] = useState({
    orderDate: '',
    total: '',
    customerId: '',
    productCodes: [] as string[] | any,
  });
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchOrders = async () => {
    try {
      const data = await getCustomerOrders();
      setOrders(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const fetchClients = async () => {
    try {
      const data = await getClients();
      setClients(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const fetchProducts = async () => {
    try {
      const data = await getProducts();
      setProducts(data);
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    fetchOrders();
    fetchClients();
    fetchProducts();
  }, []);

  const handleOpenDialog = (order: any = null) => {
    setEditOrder(order);
    setForm(order ? {
      orderDate: order.orderDate,
      total: order.total,
      customerId: order.customer,
      productCodes: order.products ? order.products.map((product: any) => product.id) : [],
    } : {
      orderDate: '',
      total: '',
      customerId: '',
      productCodes: [],
    });
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleCustomerChange = (event: SelectChangeEvent<string>) => {
    setForm({ ...form, customerId: event.target.value });
  };

  const handleProductsChange = (event: SelectChangeEvent<string[]>) => {
    setForm({ ...form, productCodes: event.target.value });
  };

  const handleSubmit = async () => {
    try {
      if (editOrder) {
        await updateCustomerOrder(editOrder.id, form);
      } else {
        await createCustomerOrder(form);
      }
      fetchOrders();
      handleCloseDialog();
    } catch (error: any) {
      console.error(error.message);
    }
  };

  const handleDelete = (id: string) => {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover this order!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await deleteCustomerOrder(id);
          setOrders(orders.filter((order: any) => order.id !== id));
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
        Customer Orders
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()} sx={{ mb: 2 }}>
        Add new order
      </Button>
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#ffffff' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#ffffff' }}>ID</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Order Date</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Total</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Customer</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Products</TableCell>
              <TableCell style={{ color: '#ffffff' }}>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {orders.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((order: any) => (
              <TableRow key={order.id} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#ffffff' }}>{order.id}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{order.orderDate}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{order.total}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{order.customer}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>
                  {order.products.map((product: any) => product.name).join(', ')}
                </TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenDialog(order)}>
                    <EditIcon />
                  </IconButton>
                  <IconButton color="secondary" onClick={() => handleDelete(order.id)}>
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
          count={orders.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#ffffff' }}
        />
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} PaperProps={{ style: { backgroundColor: '#2e2e2e', color: '#ffffff' } }}>
        <DialogTitle>{editOrder ? 'Edit Order' : 'Add New Order'}</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Order Date"
            name="orderDate"
            type='date'
            value={form.orderDate}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <TextField
            margin="dense"
            label="Total"
            name="total"
            type="number"
            value={form.total}
            onChange={handleChange}
            fullWidth
            InputProps={{ style: { color: '#ffffff' } }}
            InputLabelProps={{ style: { color: '#ffffff' } }}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel
              id="customer-select-label"
              style={{ color: '#ffffff' }}
            >
              Customer
            </InputLabel>
            <Select
              labelId="customer-select-label"
              id="customer-select"
              value={form.customerId}
              onChange={handleCustomerChange}
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
              {clients.map((client: any) => (
                <MenuItem
                  key={client.id}
                  value={client.id}
                  style={{
                    backgroundColor: '#444',
                    color: '#ffffff',
                  }}
                >
                  {client.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl fullWidth margin="dense">
            <InputLabel
              id="products-select-label"
              style={{ color: '#ffffff' }}
            >
              Products
            </InputLabel>
            <Select
              labelId="products-select-label"
              id="products-select"
              multiple
              value={form.productCodes}
              onChange={handleProductsChange}
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
              {products.map((product: any) => (
                <MenuItem
                  key={product.id}
                  value={product.id}
                  style={{
                    backgroundColor: '#444',
                    color: '#ffffff',
                  }}
                >
                  {product.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            {editOrder ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default CustomerOrders;
