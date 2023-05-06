import { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Tooltip } from 'primereact/tooltip';
import FetchHelper from "../helpers/FetchHelper";
import { OverlayPanel } from 'primereact/overlaypanel';
import Search from "./layouts/Search";
import Navbar from "./layouts/Navbar";
import Pagination from "./layouts/Pagination";
import Article from "./layouts/Article";


export default function Acceuil() {
    
    const navigate=useNavigate();
    const location=useLocation();

    const op=useRef(null);

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

    const [article,setArticle]=useState([{
        id:0,
        title:"title",
        description:"description ",
        datePub:"mai 0 0 ",
        type:"Domaine 0"
    }])

    const getArticle=()=>{
        let url="article/get/";
        if(pageActive==1){
            url+="1-6";
        }
        else{
            url+=((pageActive-1)*6)+"-"+(pageActive*6);
        }
        let fetch=FetchHelper.getData(url);
        fetch.then(response=>{
            setArticle(response);
        })
        .catch(error=>console.log(error));
    }
    

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
                        const autreFetch=FetchHelper.getData("client/getClientConnecter");
                        autreFetch.then(response=>{
                            setUtilisateur({
                                nom:response.nom,
                                prenom:response.prenom,
                                email:response.email
                            })
                        })
                        .catch(error=>{
                            console.log(error);
                        })
                    }
                })
            }
        }
        const getPageActive=()=>{
            var split=location.pathname.split('/');
            if(split.length==2){
                setActive(1);
            }
            if(split.length==3){
                setActive(split[2]);
            }
        }
        verficationSession();
        document.title="Acceuil";
        getPageActive();
        getNbrPage();
        getArticle();
    },[navigate,location]);


    

    const [utilisateur,setUtilisateur]=useState({
        nom :"",
        prenom:"",
        email:""
    });



   


    return(
    <div className="h-screen w-full font-sans bg-white relative flex overflow-hidden">
    <Tooltip target=".icone" />
    <Navbar utilisateur={utilisateur} panel={op}>
    <main className="max-w-full h-full flex relative overflow-y-hidden">
                <div className="h-full w-full p-4 m-4 items-start justify-start rounded-tl sm:grid grid-flow-row grid-cols-3 gap-4 overflow-y-scroll"> 
                    {article.map(a=>{
                        return <Article article={a}/>
                    })}
                </div>
            </main>
            <Pagination nbrPage={nbrPage} location={location} pageActive={pageActive}/>
    </Navbar>


        <OverlayPanel ref={op} showCloseIcon>
            <Search/>
        </OverlayPanel>

        
    </div>
    );

}