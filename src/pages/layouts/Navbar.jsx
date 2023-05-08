import { Button } from "primereact/button";
import { useImperativeHandle } from "react";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import image from "./../../assets/img/default.png";
import { Avatar } from 'primereact/avatar';
import { Menu } from 'primereact/menu';
import { classNames } from "primereact/utils";
import { useState } from "react";
        
export default function Navbar(props) {

    const menu=useRef(null);
    const menubuttonRef = useRef(null);
    const topbarmenuRef = useRef(null);
    const topbarmenubuttonRef = useRef(null);
    const [isMenuHidden, setIsMenuHidden] = useState(true);
    
    const handleClick = () => {
        setIsMenuHidden(!isMenuHidden);
      };
    

    let itemsMenu=[
        {
            label:"Log out",
            icon:"pi pi-sign-out",
            command:()=>{
                logOut();
            }
        }
    ]

    const navigate=useNavigate();

    const logOut=()=>{
        localStorage.removeItem("token");
        navigate("/",{
            replace:true
        })
    }





    return(
        <>
         <header className="sticky">
     <nav
        class="
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
            class="h-6 w-6 cursor-pointer md:hidden block"
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
            class="
              pt-4
              text-base text-gray-300
              md:flex
              md:justify-between 
              md:pt-0"
          >
            <li>
                <div class="md:p-4 py-2 block ">
                    <input class="bg-transparent outline-none pl-2 focus:placeholder:text-purple-400 focus:border-none focus:text-purple-400" type="text" name="search" id="search" placeholder="search ..." />
                </div>
            </li>
            <li>
              <a class="md:p-4 py-2 block hover:text-purple-400" href="#">
                <i className="pi pi-microsoft"/>  All
                </a>
            </li>
            <li>
              <a class="md:p-4 py-2 block hover:text-purple-400" href="#">
                <i className="pi pi-external-link"/>  Domaine
                </a>
            </li>
            <li>
              <button
                class="md:p-4 py-2 block hover:text-purple-400 text-purple-500"
                onClick={logOut}
                >
                <i className="pi pi-sign-out"></i> Log out
                </button>
            </li>
          </ul>
        </div>
    </nav>
  </header>



        
    </>
    );
}