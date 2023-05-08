import { useNavigate } from "react-router-dom";

import { useRef, useState } from "react";
import image from "./../../../assets/img/admin.png";
export default function NavBarAdmin() {

    const navigate=useNavigate();

    const logOut=(event)=>{
        event.preventDefault();
        localStorage.removeItem("tokenAdmin");
        navigate("/admin/login",{
            replace:true
        })
    }

    const [isMenuHidden, setIsMenuHidden] = useState(true);
    
    const handleClick = () => {
        setIsMenuHidden(!isMenuHidden);
      };
    


    return(
        <header className="sticky">
     <nav
        className="
          flex flex-wrap
          items-center
          justify-between
          w-full
          py-4
          md:py-0
          px-4
          text-lg text-gray-300
          bg-gray-700
        "
      >
       <div>
          <a href="/acceuil">
            <h1 className="flex text-2xl text-gray-100"><img src={image} className="w-12 h-w-12 mr-4"/> <span className="mt-2"> Info IA</span></h1>
          </a>
        </div>
       
        
        <svg
            xmlns="http://www.w3.org/2000/svg"
            onClick={handleClick}
            id="menu-button"
            className="h-6 w-6 cursor-pointer md:hidden block"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
    
       
       <div className={isMenuHidden ? 'hidden w-full md:flex md:items-center md:w-auto' : 'w-full md:flex md:items-center md:w-auto'}id="menu">
          <ul
            className="
              pt-4
              text-base text-gray-300
              md:flex
              md:justify-between 
              md:pt-0"
          >
            <li>
              <a className="md:p-4 py-2 block hover:text-purple-400" href="article">
                <i className="pi pi-table"/> Article
                </a>
            </li>
            <li>
              <a className="md:p-4 py-2 block hover:text-purple-400" href="domaine">
                <i className="pi pi-table"/>  Domaine
                </a>
            </li>
            <li>
              <button
                className="md:p-4 py-2 block hover:text-purple-400 text-purple-500"
                onClick={logOut}
                >
                <i className="pi pi-sign-out"></i> Log out
                </button>
            </li>
          </ul>
        </div>
    </nav>
  </header>

    );
}