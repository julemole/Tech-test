import api from '../api';

export const getCompanies = async () => {
  try {
    const response = await api.get('/companies');
    return response.data.data;
  } catch (error) {
    throw new Error('Error al cargar compañías');
  }
};

export const createCompany = async (company: any) => {
  try {
    const response = await api.post('/companies/', company);
    return response.data;
  } catch (error) {
    throw new Error('Error al crear compañía');
  }
};

export const updateCompany = async (nit: string, company: any) => {
  try {
    const response = await api.put(`/companies/${nit}`, company);
    return response.data;
  } catch (error) {
    throw new Error('Error al actualizar compañía');
  }
};

export const deleteCompany = async (nit: string) => {
  try {
    const response = await api.delete(`/companies/${nit}`);
    return response.data;
  } catch (error) {
    throw new Error('Error al eliminar compañía');
  }
};
