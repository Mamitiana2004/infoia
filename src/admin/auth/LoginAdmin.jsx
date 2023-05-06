import {  useEffect, useRef, useState } from "react";
import {InputText} from "primereact/inputtext";
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { ConfirmDialog } from 'primereact/confirmdialog';
import { Toast } from 'primereact/toast';
import { Divider } from "primereact/divider";
import { Avatar } from 'primereact/avatar';
import './../../assets/css/Login.css';
import FetchHelper from "../../helpers/FetchHelper";
import { useNavigate } from "react-router-dom";


const emailClasse=" bg-transparent h-10 text-slate-700 pl-10 pr-3 py-2 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-950 emailInput";
const passwordClasse="passwordInput";

const invalidClasse=" p-invalid ";

export default function LoginAdmin() {

    const navigate=useNavigate();

    useEffect(() => {
        document.title = 'Login Admin';
        const verficationSession=()=>{
            const token=localStorage.getItem("tokenAdmin");
            console.log(token);
            if(token!=null){
                const fetch=FetchHelper.getDataAdmin("admin/verifiSession");
                fetch.then(response=>{
                    console.log(response);
                    if(response.code==null){
                        navigate("/admin/acceuil");
                    }
                })
            }
        }
        verficationSession();
    }, [navigate]);

      

    const [state,setState]=useState({   
        user: "",
        password: ""
    });
    
    const [classes,setClasses]=useState({
        email:emailClasse,
        password:passwordClasse
    });

    const toast = useRef(null);
  
   
    
    const handleChange = (event) => {
        setClasses({
            email:emailClasse,
            password:passwordClasse
        });
        const { name, value } = event.target;
        setState(prevState => ({ ...prevState, [name]: value }));
    };




    const sendData=()=> {
        // Envoi des données au serveur
        var donne=new URLSearchParams();
        donne.append("nom",state.user);
        donne.append("password",state.password);

        const fetch=FetchHelper.getDataPostAdmin("admin/login",donne);
        fetch.then(response=>{
            console.log(response);
            if(response.code!=null){
                if(response.code===100){
                    let classeFinal=passwordClasse+" "+invalidClasse
                    setClasses(prevState => ({ ...prevState,password:classeFinal}));
                }
                else{
                    let classeFinal=emailClasse+" "+invalidClasse
                    setClasses({email:classeFinal,password:""});
                }
                toast.current.show({severity:'error', summary: 'Error', detail:response.message, life: 3000});
            }
            else{
                toast.current.show({severity:'success', summary: 'Success', detail:'Demande reçu', life: 3000});
                localStorage.setItem("tokenAdmin",response.token);
                navigate("/admin/acceuil");
            }
            console.log(response);
        })
        .catch(error=>{
            console.log(error);
            toast.current.show({severity:'error', summary: 'Error', detail:'Connection echoué,verifiez votre connection', life: 3000});
        });

    }

    const handleSubmit = (event) => {
        event.preventDefault();
        sendData();
    };


    return(
            <div className="min-w-screen min-h-screen bg-gray-900 flex items-center justify-center px-5 py-5 ">
                <div className=" rounded-3xl shadow-xl overflow-hidden">
                    <div className="border-y-8 border-slate-800 border-solid shadow-xl" style={{ borderRadius: '56px', padding: '0.3rem', background: 'linear-gradient(180deg, #069 10%, rgba(33, 150, 243, 0) 30%)' }}>
                        <div className="form-container surface-card py-8 px-5 sm:px-8" style={{ borderRadius: '53px' }}>
                            <div className="text-center text-gray-300 mb-5">
                                <Avatar icon="pi pi-sign-in" size="xlarge" className=" text-slate-800" style={{backgroundColor:'#d1d5db'}} shape="circle" />
                                <div className="text-900 text-3xl font-medium mb-3">Welcome back!</div>
                                <span className="text-600 text-gray-500 font-medium">Sign in to continue</span>
                            </div>
                            <div>
                                <form onSubmit={handleSubmit}>
                                    <label htmlFor="user" className="block text-gray-200 text-900 text-sm font-medium font-sans mb-2">
                                        Username
                                    </label>
                                    <InputText required inputid="user" type="text" onChange={handleChange}  placeholder="Username" name="user" value={state.user} className={classes.email} style={{ padding: '1rem' }} />
                                    <label htmlFor="password1" className="block text-gray-200 text-900 font-medium text-sm mb-2">
                                        Password
                                    </label>
                                    <Password required inputid="password1" onChange={handleChange} placeholder="Password" tooltip="Enter your password"  name="password" value={state.password} toggleMask className={classes.password} inputClassName="passwordInput h-10 rounded-lg border-2 border-gray-200 outline-none focus:border-blue-950" ></Password>
                                    <br /><br />
                                    <Button label="Sign In" className="loginBtn h-10 font-medium text-sm"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                
                <Toast ref={toast}  position="bottom-left"/>
                <ConfirmDialog />

                

            </div>
        
    );

}