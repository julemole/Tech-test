import React from 'react';
import { useTranslation } from 'react-i18next';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

const LanguageSelector: React.FC = () => {
  const { i18n } = useTranslation();

  const changeLanguage = (lng: string) => {
    i18n.changeLanguage(lng);
  };

  return (
    <Stack direction="row" spacing={2}>
      <Button variant="contained" onClick={() => changeLanguage('en')}>
        English
      </Button>
      <Button variant="contained" onClick={() => changeLanguage('es')}>
        Español
      </Button>
    </Stack>
  );
};

export default LanguageSelector;