import { Button } from "primereact/button";
import { Divider } from "primereact/divider";
import { useNavigate } from "react-router-dom";
import NavBarAdmin from "./layouts/NavBarAdmin";

export default function AcceuilAdmin(){

    
    const navigate=useNavigate();


    return(
        <div>
            <NavBarAdmin/>
            
        </div>
    );
}