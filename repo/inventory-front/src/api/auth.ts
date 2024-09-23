import instance from './index';

export const login = async (data: any) => {
  try {
    const response = await instance.post('/auth/login', data);
    return response.data;
  } catch (error) {
    console.error("Error en la petición de login:", error);
    throw error;
  }
};
