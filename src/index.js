import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';


import reportWebVitals from './reportWebVitals';



import './assets/css/index.css';
import './assets/css/style.css';
import 'primereact/resources/themes/lara-dark-purple/theme.css';
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";        
import App from './pages/App';
import Acceuil from './pages/Acceuil';
import LoginAdmin from './admin/auth/LoginAdmin';
import AcceuilAdmin from './admin/pages/AcceuilAdmin';
import VoirArticle from './pages/VoirArticle';
import ArticleCrud from './admin/pages/crud/ArticleCrud';
import DomaineCrud from './admin/pages/crud/DomaineCrud';
import UploadPhoto from './admin/pages/UploadPhoto';
import Error404 from './pages/error/Error404';
                                 
        
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <BrowserRouter>
    <Routes>
      <Route exact path={'/'} element={<App/>}/>
      <Route path={'/acceuil/:page?'} element={<Acceuil/>}/>
      <Route path={'/article/:details'} element={<VoirArticle/>}/>



      <Route exact path={'/admin/login'} element={<LoginAdmin/>}/>
      <Route exact path={'/admin/acceuil'} element={<AcceuilAdmin/>}/>
      <Route exact path={'/admin/article'} element={<ArticleCrud/>}/>
      <Route exact path={'/admin/domaine'} element={<DomaineCrud/>}/>
      <Route exact path={'/admin/upload'} element={<UploadPhoto/>}/>




      <Route path='*' element={<Error404/>}/>
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
