import React, { useState, useEffect } from 'react';
import {
  Typography, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
  TablePagination
} from '@mui/material';
import { getProducts } from '../services/productService';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { useTranslation } from 'react-i18next';
import styles from '../styles/home.module.css';

const Inventory: React.FC = () => {
  const { t } = useTranslation();
  const [products, setProducts] = useState<any[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<any[]>([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const fetchProducts = async () => {
    try {
      const data = await getProducts();
      setProducts(data);
      setFilteredProducts(data.filter((product: any) => product.isInInventory));
    } catch (error: any) {
      console.error(error.message);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDownloadPDF = () => {
    const doc = new jsPDF();
    const tableColumn = [t("code"), t("name"), t("description"), t("price"), t("company"), t("categories")];
    const tableRows: any[] = [];

    filteredProducts.forEach((product: any) => {
      const productData = [
        product.code,
        product.name,
        product.description,
        product.price,
        product.company.name,
        product.categories.map((category: any) => category.name).join(', ')
      ];
      tableRows.push(productData);
    });

    (doc as any).autoTable({
      head: [tableColumn],
      body: tableRows,
      startY: 20,
    });

    doc.text(t("inventoryProducts"), 14, 15);
    doc.save(`inventory_products_${new Date().toISOString()}.pdf`);
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
        {t('inventoryProducts')}
      </Typography>
      <div style={{ display: 'flex', justifyContent: 'end'}}>
      <Button variant="contained" color="primary" onClick={handleDownloadPDF} sx={{ mb: 2 }}>
        {t('downloadPDF')}
      </Button>
      </div>
      <TableContainer component={Paper} style={{ backgroundColor: '#1e1e1e', color: '#ffffff' }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell style={{ color: '#ffffff' }}>{t('code')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('name')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('description')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('price')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('company')}</TableCell>
              <TableCell style={{ color: '#ffffff' }}>{t('categories')}</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredProducts.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((product: any) => (
              <TableRow key={product.code} style={{ backgroundColor: '#2e2e2e' }}>
                <TableCell style={{ color: '#ffffff' }}>{product.code}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.name}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.description}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.price}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>{product.company.name}</TableCell>
                <TableCell style={{ color: '#ffffff' }}>
                  {product.categories.map((category: any) => category.name).join(', ')}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25]}
          component="div"
          count={filteredProducts.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
          style={{ color: '#ffffff' }}
        />
      </TableContainer>
    </div>
  );
};

export default Inventory;