import { useEffect, useRef } from "react"
import { Loading } from "./layouts/Loading";
import { useNavigate } from "react-router-dom";
import FetchHelper from './../helpers/FetchHelper'
import { Toast } from "primereact/toast";
export default function App() {
    
    const navigate=useNavigate();
    const toast=useRef();

    useEffect(()=>{
        const verficationSession=()=>{
            const token=localStorage.getItem("token");
            if(token==null){
                navigate("/login");
            }
            else{
                const fetch=FetchHelper.getData("verifiSession");
                fetch.then(response=>{
                    if(response.code!=null){
                        navigate("/login");
                    }
                    else{
                        navigate("/acceuil");
                    }
                })
            }
        }
        verficationSession();
    },[navigate]);


    


    return(
        <>
            <Loading/>
            <Toast ref={toast}/>
        </>
    )

}