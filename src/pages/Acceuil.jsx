import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Tooltip } from 'primereact/tooltip';
import FetchHelper from "../helpers/FetchHelper";
import { OverlayPanel } from 'primereact/overlaypanel';
import Search from "./layouts/Search";
import Navbar from "./layouts/Navbar";
import Pagination from "./layouts/Pagination";
import Article from "./layouts/Article";
import AppFooter from "./layouts/AppFooter";


export default function Acceuil() {
    
    const navigate=useNavigate();
    const location=useLocation();

    const op=useRef(null);

    const [domaine,setDomaine]=useState([]);

    const getAllDomaine=()=>{
        const fetch=FetchHelper.getData("domaine/getAll");
        fetch.then(response=>{
            setDomaine(response);
        })
        .catch(error=>console.log(error));
    }

    const [nbrPage,setNbrPage]=useState(0);

    const getNbrPage=()=>{
        let fetch=FetchHelper.getData("article/count");
        fetch.then(response=>{
            let nbrDonne=response.data;
            let nr=Math.round(nbrDonne/6);
            setNbrPage(nr);
        })
        .catch(error=>console.log(error));
    }

    const [pageActive,setActive]=useState(1);

    const [article,setArticle]=useState([])

    const getArticle=()=>{
        let url="article/get/";
        if(pageActive==1){
            url+="1-3";
        }
        else{
            url+=((pageActive-1)*3)+"-"+(pageActive*3);
        }
        let fetch=FetchHelper.getData(url);
        fetch.then(response=>{
            setArticle(response);
        })
        .catch(error=>console.log(error));
    }
    

    useEffect(()=>{
        const getPageActive=()=>{
            var split=location.pathname.split('/');
            if(split.length==2){
                setActive(1);
            }
            if(split.length==3){
                setActive(split[2]);
            }
        }
        getAllDomaine();
        document.title="Acceuil";
        getPageActive();
        getNbrPage();
        getArticle();
    },[]);


    

    const [utilisateur,setUtilisateur]=useState({
        nom :"",
        prenom:"",
        email:""
    });



   


    return(
    <div>
    <Tooltip target=".icone" />
    <Navbar utilisateur={utilisateur} domaine={domaine} panel={op}>
    
    </Navbar>
        <section class="flex bg-gray-900 flex-col justify-center max-w-6xl min-h-screen px-4 py-10 mx-auto sm:px-6">
            <div class="flex flex-wrap -mx-4">
            {article.map(a=>{
                return <Article article={a}/>
            })}
            </div>
        </section>
        <Pagination nbrPage={nbrPage} location={location} pageActive={pageActive}/>

        <OverlayPanel ref={op} showCloseIcon>
            <Search domaine={domaine}/>
        </OverlayPanel>

        <AppFooter/>
    </div>
    );

}