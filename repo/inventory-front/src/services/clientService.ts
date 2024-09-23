import api from '../api';

export const getClients = async () => {
  const response = await api.get('/customers', {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data.data;
};

export const createClient = async (client: any) => {
  const response = await api.post('/customers/', client, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data;
};

export const updateClient = async (id: number, client: any) => {
  const response = await api.put(`/customers/${id}`, client, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data;
};

export const deleteClient = async (id: number) => {
  const response = await api.delete(`/customers/${id}`, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  return response.data;
};
