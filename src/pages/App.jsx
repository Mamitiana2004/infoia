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
            const token=localStorage.getItem("tokenAdmin");
            if(token==null){
                navigate("/admin/login");
            }
            else{
                const fetch=FetchHelper.getData("admin/verifiSession");
                fetch.then(response=>{
                    if(response.code!=null){
                        toast.current.show({severity:'error', summary: 'Error', detail:"Session expiré", life: 3000});
                        setTimeout(() => {
                            navigate("/admin/login");
                        }, 5000);
                    }
                    else{
                        navigate("/admin/acceuil");
                    }
                }).catch(toast.current.show({severity:'error', summary: 'Error', detail:"Connection échoué", life: 3000}))
            }
        }
        verficationSession();
    },[navigate]);


    


    return(
        <>
            <Loading/>
            <Toast ref={toast} position="top-center"/>
        </>
    )

}