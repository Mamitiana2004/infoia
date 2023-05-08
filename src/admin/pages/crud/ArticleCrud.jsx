import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import FetchHelper from "../../../helpers/FetchHelper";

import { InputTextarea } from 'primereact/inputtextarea';
        
import { Dropdown } from 'primereact/dropdown';
        
        
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from "primereact/button";
import { Dialog } from "primereact/dialog";
import { InputText } from "primereact/inputtext";
import NavBarAdmin from "../layouts/NavBarAdmin";
        
export default function ArticleCrud() {

    const navigate=useNavigate();

    const [article,setArticle]=useState([{
        id:0,
        title:"title",
        description:"description ",
        content:"",
        datePub:"mai 0 0 ",
        type:"Domaine 0",
        photo:""
    }]);

    const getArticle=()=>{
        let url="article/getAll";
        let fetch=FetchHelper.getDataAdmin(url);
        fetch.then(response=>{
            console.log(response);
            setArticle(response.data);
        })
        .catch(error=>console.log(error));
    }

    const [state,setState]=useState({
        title:"",
        description:"",
        content:"",
        datePub:""
    });

    
    const getAllDomaine=()=>{
        const fetch=FetchHelper.getData("domaine/getAll");
        fetch.then(response=>{
            setDomaine(response);
        })
        .catch(error=>console.log(error));
    }
    const handleChange = (event) => {
        const { name, value } = event.target;
        setState(prevState => ({ ...prevState, [name]: value }));
    };

    
    const [domaine,setDomaine]=useState([]);    

    useEffect(() => {
        document.title = 'Article crud';
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
        getArticle();
    }, [navigate]);
    
    const handleSubmit=(event)=>{
        event.preventDefault();
        let donne=new FormData();
        donne.append("title",state.title);
        donne.append("description",state.description);
        donne.append("content",state.content);
        donne.append("datePub",state.datePub);
        donne.append("domaine",selectDomaine);
        donne.append("photo",selectedFile);
        console.log(state);
        console.log(selectedFile);
        console.log(selectDomaine);
        fetch('https://infoia-production-b910.up.railway.app/article/add', {
            method: 'POST',
            body: donne
          })
          .then(response => response.json())
          .then(res=>{
            console.log(res);
          })
          .catch(error => {
            // Gérer l'erreur
            console.log(error);
          });
    }

    const [selectDomaine,setSelectDomaine]=useState(null);

    const deleteArticle=(id)=>{
        let donne=new URLSearchParams();
        donne.append("id",id);
        FetchHelper.getDataPostAdmin("article/delete",donne).then(response=>{
            console.log(response);
        }).catch(eror=>{
            console.log(eror);
        })
    }

    const imageBodyTemplate = (article) => {
        return <img src={`data:image/png;base64,${article.photo}`} alt={article.title} className="w-48 shadow-2 border-round" />;
    };

    const supprimerBody = (article) => {
        return (
        <div className="flex gap-2">
            <Button icon="pi pi-pencil" rounded severity="success" aria-label="modifier" />
            <Button icon="pi pi-trash" rounded severity="danger" aria-label="supprimer" />
        </div>)
    };

    const add = (article) => {
        return <Button icon="pi pi-plus" rounded severity="help" aria-label="Cancel"  onClick={() => setVisible(true)}  />;
    };

    const [visible,setVisible]=useState(false);
    const [selectedFile,setSelectedFile]=useState(null);

    const handleChangeFile=(event)=>{
        setSelectedFile(event.target.files[0]);
    }

    return(
        <div>
            <NavBarAdmin/>  
            <div className="card  justify-content-center">
                <Dialog position="left" header="Ajouter une nouvel article" maximizable visible={visible} style={{ width: '50vw' }} onHide={() => setVisible(false)}>
                    <form onSubmit={handleSubmit}>    
                        <span className="flex flex-col m-6 p-float-label">
                            <InputText required className="inputClasse" id="nom" onChange={handleChange} value={state.title} name="title"/>
                            <label htmlFor="nom">Titre</label>
                        </span>

                        <span className="flex flex-col m-6 p-float-label">
                            <InputText required className="inputClasse" id="nom" onChange={handleChange} value={state.description} name="description"/>
                            <label htmlFor="nom">Description</label>
                        </span>

                        <span className="flex flex-col m-6 ">
                            <InputText type="date" required className="inputClasse" id="nom" onChange={handleChange} value={state.datePub} name="datePub"/>
                            <label htmlFor="nom">Date de publication</label>
                        </span>

                        <span className="flex flex-col m-6 p-float-label">
                            <InputTextarea required className="inputClasse" id="nom" onChange={handleChange} value={state.content} name="content"/>
                            <label htmlFor="nom">Content</label>
                        </span>

                        <span className="flex flex-col m-6">
                            <input type="file" accept="image/*" required className="inputClasse" id="nom" onChange={handleChangeFile} name="selectedFile"/>
                        </span>

                        <span className="flex flex-col m-6">
                            <Dropdown value={selectDomaine} onChange={(e) => setSelectDomaine(e.value)} options={domaine} optionLabel="type" optionValue="id" placeholder="Selecte un domaine" className="w-full md:w-14rem"/>
                        </span>





                        <span className="flex flex-col m-6">
                            <Button type="submit" label="Add" icon="pi pi-plus" />
                        </span>
                    </form>

                </Dialog>
            </div>
            <div className="w-full h-full">
                    <DataTable value={article} paginator stripedRows rows={5}  sortMode="multiple" emptyMessage="No article found." rowsPerPageOptions={[5, 10, 25, 50]} removableSort scrollable tableClassName="w-full h-full" tableStyle={{ minWidth: '50rem' }}>
                        <Column field="id" sortable header="ID"></Column>
                        <Column field="title" header="Titre"></Column>
                        <Column header="Image" body={imageBodyTemplate}></Column>
                        <Column field="description" header="Description"></Column>
                        <Column field="content" header="Content"></Column>
                        <Column field="datePub" sortable header="Date de publication"></Column>
                        <Column field="type" header="Domaine"></Column>  
                        <Column header={add} body={supprimerBody}></Column>   
                    </DataTable>
            </div>
        </div>
    );

}