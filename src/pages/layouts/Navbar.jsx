import { useNavigate } from "react-router-dom";

export default function Navbar(props) {

    const navigate=useNavigate();

    const logOut=(event)=>{
        event.preventDefault();
        localStorage.removeItem("token");
        navigate("/",{
            replace:true
        })
    }



    return(
        <>
            <div>
                <aside className="h-full w-16 flex flex-col space-y-10 items-center justify-center relative bg-gray-800 text-white">
                    
                    <div className="h-10 w-10 flex items-center justify-center rounded-lg cursor-pointer hover:text-gray-800 hover:bg-white  hover:duration-300 hover:ease-linear focus:bg-white">
                        <i className="icone pi pi-list" 
                            data-pr-tooltip="Domaine"
                            data-pr-position="right"
                            data-pr-at="right+5 top"
                            data-pr-my="left center-2"
                        />
                    </div>

                    
                    <div onClick={(e) => props.panel.current.toggle(e)} className="h-10 w-10 flex items-center justify-center rounded-lg cursor-pointer hover:text-gray-800 hover:bg-white  hover:duration-300 hover:ease-linear focus:bg-white">
                        <i className="pi pi-search"/>
                    </div>
                    

                    
                    <div onClick={logOut} className="h-10 w-10 flex items-center justify-center rounded-lg cursor-pointer hover:text-gray-800 hover:bg-white  hover:duration-300 hover:ease-linear focus:bg-white">
                        <i className="icone pi pi-sign-out" 
                            data-pr-tooltip="Log out"
                            data-pr-position="right"
                            data-pr-at="right+5 top"
                            data-pr-my="left center-2"/>
                    </div>
                </aside>

            </div>

            <div className="w-full h-full flex flex-col justify-between">
                <header className="h-16 w-full flex items-center relative justify-end px-5 space-x-10 bg-gray-800">
                    
                    <div className="flex flex-shrink-0 items-center space-x-4 text-white">
                        
                        <div className="flex flex-col items-end ">
                        
                        <div className="text-md font-medium ">{props.utilisateur.nom+" "+props.utilisateur.prenom}</div>

                        <div className="text-sm font-regular">{props.utilisateur.email}</div>
                        
                        </div>
                        
                    
                        <div className="h-10 w-10 pt-2 rounded-full cursor-pointer text-center text-gray-200  bg-gray-900 border-2 border-gray-400">
                            <i className="pi pi-user"/>
                        </div>
                    </div>
                </header>

                {props.children}

            </div>



        
    </>
    );
}