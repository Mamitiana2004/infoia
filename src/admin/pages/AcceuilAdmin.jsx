import { Button } from "primereact/button";
import { Divider } from "primereact/divider";
import { useNavigate } from "react-router-dom";

export default function AcceuilAdmin(){

    
    const navigate=useNavigate();

    const logOut=(event)=>{
        event.preventDefault();
        localStorage.removeItem("tokenAdmin");
        navigate("/admin/login",{
            replace:true
        });
    }

    return(
        <div>
            <button onClick={logOut}>Log out</button>
            <Divider/>
            <a href="/admin/article"><Button label="ajouter une article"/></a>
        </div>
    );
}