import api from '../api';

export const getCategories = async () => {
  try {
    const response = await api.get('/categories');
    return response.data.data;
  } catch (error) {
    throw new Error('Error fetching categories');
  }
};

export const createCategory = async (category: any) => {
  try {
    const response = await api.post('/categories/', category);
    return response.data;
  } catch (error) {
    throw new Error('Error creating category');
  }
};

export const updateCategory = async (id: string, category: any) => {
  try {
    const response = await api.put(`/categories/${id}`, category);
    return response.data;
  } catch (error) {
    throw new Error('Error updating category');
  }
};

export const deleteCategory = async (id: string) => {
  try {
    const response = await api.delete(`/categories/${id}`);
    return response.data;
  } catch (error) {
    throw new Error('Error deleting category');
  }
};
