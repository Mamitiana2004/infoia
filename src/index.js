import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Login from './pages/auth/Login';
import Sign from './pages/auth/Sign';


import reportWebVitals from './reportWebVitals';



import './assets/css/index.css';
import "primereact/resources/themes/md-dark-indigo/theme.css";     
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";        
import App from './pages/App';
import Acceuil from './pages/Acceuil';
import LoginAdmin from './admin/auth/LoginAdmin';
import AcceuilAdmin from './admin/pages/AcceuilAdmin';
                                 
        
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
    <Routes>
      <Route exact path={'/'} element={<App/>}/>
      <Route exact path={'/login'} element={<Login/>}/>
      <Route exact path={'/sign'} element={<Sign/>}/>
      <Route path={'/acceuil/:page?'} element={<Acceuil/>}/>



      <Route exact path={'/admin/login'} element={<LoginAdmin/>}/>
      <Route exact path={'/admin/acceuil'} element={<AcceuilAdmin/>}/>
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
