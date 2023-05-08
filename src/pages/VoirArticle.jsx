import { Tooltip } from "primereact/tooltip";
import Navbar from "./layouts/Navbar";
import FetchHelper from "../helpers/FetchHelper";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
import { Divider } from "primereact/divider";




export default function VoirArticle() {

    
    const navigate=useNavigate();

    const op=useRef(null);

    const details=useParams();

    const [utilisateur,setUtilisateur]=useState({
        nom :"",
        prenom:"",
        email:""
    });

    const getArticle=()=>{
        let url="article/getById/"+details.details.split("-")[1];
        console.log(url);
        let fetch=FetchHelper.getData(url);
        fetch.then(response=>{
            console.log(response);
            setArticle(response);
        })
        .catch(error=>console.log(error));
    }

    const [article,setArticle]=useState({
        id:0,
        title:"title",
        description:"description ",
        content:"",
        datePub:"mai 0 0 ",
        type:"Domaine 0",
        photo:""
    })

    useEffect(()=>{
        getArticle();
        document.title="Article ";
    },[navigate]);


    return(
        <div className="h-screen w-full  bg-white relative flex overflow-hidden">
            <Tooltip target={"icone"}/>
            <Navbar utilisateur={utilisateur} panel={op}>
                <main className="w-full h-full flex relative overflow-y-hidden">
                        <div className="w-full max-w-6xl rounded bg-gray-900 shadow-xl p-10 lg:p-20 mx-auto text-gray-200 relative md:text-left">
                            <div className="md:flex items-center -mx-10">
                                <div className="w-full md:w-1/2 px-10 mb-10 md:mb-0">
                                    <div className="relative">
                                        <img src={`data:image/png;base64,${article.photo}`} alt={article.photo}  className="w-full relative z-10" />
                                        <div className="border-4 border-yellow-200 absolute top-10 bottom-10 left-10 right-10 z-0"></div>
                                    </div>
                                </div>
                                <div className="w-full md:w-1/2 px-10">
                                    <div className="mb-10">
                                        <div className="mb-5">
                                            <h1 className="font-bold uppercase text-2xl">{article.title}</h1>
                                            <h3 className="font-semibold text-xl">{article.description}</h3>
                                        </div>
                                        <p className="text-sm">
                                            {article.content}
                                        </p>
                                    </div>
                                    <div>
                                        <div className="inline-block align-bottom">
                                            <button className="bg-yellow-300 opacity-75 hover:opacity-100 text-yellow-900 hover:text-gray-900 rounded-full px-10 py-2 font-semibold"><i className="mdi mdi-cart -ml-2 mr-2"></i>Retour</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                </main>
            </Navbar>
        </div>
    )
}