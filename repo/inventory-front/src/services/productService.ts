import api from '../api';

export const getProducts = async () => {
  try {
    const response = await api.get('/products');
    return response.data.data;
  } catch (error) {
    throw new Error('Error al cargar productos');
  }
};

export const createProduct = async (product: any) => {
  try {
    const response = await api.post('/products/', product);
    return response.data;
  } catch (error) {
    throw new Error('Error al crear producto');
  }
};

export const updateProduct = async (code: string, product: any) => {
  try {
    const response = await api.put(`/products/${code}`, product);
    return response.data;
  } catch (error) {
    throw new Error('Error al actualizar producto');
  }
};

export const deleteProduct = async (code: string) => {
  try {
    const response = await api.delete(`/products/${code}`);
    return response.data;
  } catch (error) {
    throw new Error('Error al eliminar producto');
  }
};
