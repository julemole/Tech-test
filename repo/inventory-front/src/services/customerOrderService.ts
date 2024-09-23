import api from '../api';

export const getCustomerOrders = async () => {
  try {
    const response = await api.get('/orders');
    return response.data.data;
  } catch (error) {
    throw new Error('Error al cargar pedidos de clientes');
  }
};

export const createCustomerOrder = async (order: any) => {
  try {
    const response = await api.post('/orders/', order);
    return response.data;
  } catch (error) {
    console.log(error)
    throw new Error('Error al crear pedido de cliente');
  }
};

export const updateCustomerOrder = async (id: string, order: any) => {
  try {
    const response = await api.put(`/orders/${id}`, order);
    return response.data;
  } catch (error) {
    throw new Error('Error al actualizar pedido de cliente');
  }
};

export const deleteCustomerOrder = async (id: string) => {
  try {
    const response = await api.delete(`/orders/${id}`);
    return response.data;
  } catch (error) {
    throw new Error('Error al eliminar pedido de cliente');
  }
};