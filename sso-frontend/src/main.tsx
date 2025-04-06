import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import { ApiProvider } from '@reduxjs/toolkit/query/react'
import { ssoApi } from './api/index.ts'
import { MantineProvider } from '@mantine/core'
import '@mantine/core/styles.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <MantineProvider>
      <ApiProvider api={ssoApi}>
        <BrowserRouter>
        <Routes>
          <Route path='*' element={<App />}/>
        </Routes>
        </BrowserRouter>
      </ApiProvider>
    </MantineProvider>
  </StrictMode>,
)
