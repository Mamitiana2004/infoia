import { useEffect, useState } from "react";
import FetchHelper from "../../../helpers/FetchHelper";
import { useNavigate } from "react-router-dom";

export default function DomaineCrud() {

    const navigate=useNavigate();

    const getAllDomaine=()=>{
        const fetch=FetchHelper.getData("domaine/getAll");
        fetch.then(response=>{
            setDomaine(response);
        })
        .catch(error=>console.log(error));
    }

    const [state,setState]=useState({
        domaine:"",
    });

    useEffect(() => {
        document.title = 'Domaine crud';
        const verficationSession=()=>{
            const token=localStorage.getItem("tokenAdmin");
            if(token==null){
                navigate("/admin/login");
            }
            if(token!=null){
                const fetch=FetchHelper.getDataAdmin("admin/verifiSession");
                fetch.then(response=>{
                    console.log(response);
                    if(response.code!=null){
                        navigate("/admin/login");
                    }
                })
            }
        }
        verficationSession();
        getAllDomaine();
    }, [navigate]);
    
    const [domaine,setDomaine]=useState([]);   

    const handleChange=(event)=>{
        const { name, value } = event.target;
        setState(prevState => ({ ...prevState, [name]: value }));
    }

    const handleSubmit=()=>{
        let donne=new URLSearchParams();
        donne.append("domaine",state.domaine);
        FetchHelper.getDataPostAdmin("domaine/add",donne).then(response=>{
            console.log(response);
        }).catch(eror=>{
            console.log(eror);
        });
    }

    const deleteDomaine=(id)=>{
        let donne=new URLSearchParams();
        donne.append("id",id);
        FetchHelper.getDataPostAdmin("domaine/delete",donne).then(response=>{
            console.log(response);
        }).catch(eror=>{
            console.log(eror);
        })
    }

    return(
        <div>
            <div>
                <form onSubmit={handleSubmit}>
                    <p>nom : <input type="text" name="domaine" onChange={handleChange} value={state.domaine}  /></p>
                    <button>add</button>
                </form>
            </div>
            <div>
                <table>
                    <tr>
                        <th>id</th>
                        <th>Nom</th>
                        <th></th>
                    </tr>
                    {domaine.map(d=>{
                        return(
                            <tr>
                                <td>{d.id}</td>
                                <td>{d.type}</td>
                                <td><button onClick={()=>{deleteDomaine(d.id)}}>supprimer</button></td>
                            </tr>
                        )
                    })}
                </table>
            </div>
        </div>
    );
}