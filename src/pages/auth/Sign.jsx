import { useEffect, useRef, useState } from "react";
import {InputText} from "primereact/inputtext";
import { Password } from 'primereact/password';
import { Divider } from 'primereact/divider';
import { ToggleButton } from 'primereact/togglebutton';
import { Button } from 'primereact/button';
import {  ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import { differenceInYears } from 'date-fns';
import { Toast } from "primereact/toast";
import { Avatar } from "primereact/avatar";
import './../../assets/css/Sign.css';
import FetchHelper from "../../helpers/FetchHelper";
import { useNavigate } from "react-router-dom";
import { Loading } from "../layouts/Loading";

        

export default function Sign() {

  const navigate=useNavigate();
  const [maleChoice,setChoice]=useState(true);

  const toast = useRef(null);

  useEffect(() => {
    document.title = 'Sign';
  }, []);

  useEffect(()=>{
    const verficationSession=()=>{
        const token=localStorage.getItem("token");
        if(token!=null){
            const fetch=FetchHelper.getData("verifiSession");
            fetch.then(response=>{
                if(response.code==null){
                    navigate("/acceuil");
                }
            })
        }
    }
    verficationSession();
  },[navigate]);

  const accept = () => {
      toast.current.show({ severity: 'info', summary: 'Confirmed', detail: 'Demande d\'inscription envoyé', life: 1000 });
      sendData();
  }

  const reject = () => {
      toast.current.show({ severity: 'warn', summary: 'Rejected', detail: 'Demande annulé', life: 1000 });
  }

  const showError = () => {
    toast.current.show({severity:'error', summary: 'Error', detail:'Age minimum 18', life: 3000});
  }


  const [state, setState] = useState({
    nom: "",
    prenom: "",
    genre: "1",
    dateNaissance:"",
    email: "",
    password: ""
  });


  const footer = (
        <>
            <Divider />
            <p className="mt-2">Suggestions</p>
            <ul className="pl-2 ml-2 mt-0 line-height-3">
                <li>At least one lowercase</li>
                <li>At least one uppercase</li>
                <li>At least one numeric</li>
                <li>Minimum 8 characters</li>
            </ul>
        </>
  );

  


  const handleChange = (event) => {
    const { name, value } = event.target;
    setState(prevState => ({ ...prevState, [name]: value }));
  };


  const confirm1 = () => {
      confirmDialog({
          message: 'Are you sure you want to proceed?',
          header: 'Confirmation',
          icon: 'pi pi-exclamation-triangle',
          accept,
          reject
      });
  };


  const sendData=()=> {
    
    // Envoi des données au serveur
    var donne=new URLSearchParams();
    donne.append("nom",state.nom);
    donne.append("prenom",state.prenom);
    donne.append("genre",state.genre);
    donne.append("dateNaissance",state.dateNaissance);
    donne.append("email",state.email);
    donne.append("password",state.password);

    const fetch=FetchHelper.getDataPost("sign",donne);

    fetch.then(response=>{
        if(response.code!=null){
          toast.current.show({severity:'error', summary: 'Error', detail:response.message, life: 3000});
        }
        else{
            console.log(response);
            toast.current.show({severity:'success', summary: 'Success', detail:'Demande reçu', life: 3000});
            localStorage.setItem("token",response.token);
            navigate("/acceuil");
        }
      }
    )
    .catch(error=>{
      console.log(error);
      toast.current.show({severity:'error', summary: 'Error', detail:'Connection echoué,verifiez votre connection', life: 3000});
    });
  }

  const genreChoix=(event)=>{
    setChoice(event.value);
    if(event.value===false){
      setState(prevState => ({ ...prevState, genre: "2" }));
    }
    else{
      setState(prevState => ({ ...prevState, genre: "1" }));
    }
  }

  function isOver18() {
    if(state.dateNaissance===""){
      return false;
    }
    else{
      let age=differenceInYears(new Date(),new Date(state.dateNaissance));
      console.log(age +" ans");
      return age>=18;
    }
  }
  

  const handleSubmit = (event) => {
    event.preventDefault();
    if(!isOver18()){
      showError();
    }else{
      confirm1();
    }
    
  };


  return (
    <div>
      {
        FetchHelper.loading ?(
            <Loading/>
        ):(
          <div className="min-w-screen min-h-screen bg-gray-900 flex items-center justify-center px-5 py-5 ">
                      <div className=" rounded-3xl shadow-xl overflow-hidden">
                          <div className="border-y-8 border-slate-800 border-solid shadow-xl" style={{ borderRadius: '56px', padding: '0.3rem', background: 'linear-gradient(180deg, #069 10%, rgba(33, 150, 243, 0) 30%)' }}>
                              <div className="form-container surface-card py-8 px-5 sm:px-8" style={{ borderRadius: '53px' }}>
                                  <div className="text-center text-gray-300 mb-5">
                                      <Avatar icon="pi pi-user-plus" size="xlarge" className=" text-slate-800" style={{backgroundColor:'#d1d5db'}} shape="circle" />
                                      <div className="text-900 text-3xl font-medium mb-3">Welcome</div>
                                      <span className="text-600 text-gray-500 font-medium">Sign up</span>
                                  </div>
                                  <br /><br />
                                  <div>
                                      <form onSubmit={handleSubmit}>

                                        <div className="grid grid-cols-2 items-baseline space-x-2 mt-1 mb-2 pb-2 ">
                                            <span className="w-full p-float-label">
                                              <InputText required className="inputClasse" id="nom" keyfilter={"alpha"} onChange={handleChange} value={state.nom} name="nom"/>
                                              <label htmlFor="nom">Nom</label>
                                            </span>
                                            <span className="p-float-label">
                                                <InputText required className="inputClasse" id="prenom" keyfilter={"alpha"} onChange={handleChange} value={state.prenom} name="prenom"  />
                                                <label htmlFor="prenom">Prenom</label>
                                            </span>
                                        </div>
                                        <div className="grid mt-1 mb-2 pb-2">
                                          <span className="p-input-icon-left w-full">
                                              <InputText required className="w-full" type="date" placeholder="Date Naissance" onChange={handleChange} value={state.dateNaissance} name="dateNaissance"  title="Entrez votre prenom" />
                                          </span>
                                        </div>

                                        <ToggleButton 
                                          className="w-full mt-1 mb-2 pb-2"
                                          onLabel="Homme" 
                                          offLabel="Femme" 
                                          checked={maleChoice} 
                                          onChange={genreChoix} 
                                          title="Choisir votre genre"/>
                                          <br /><br /><hr /><br />
                                        <div className="grid grid-cols-2 items-baseline space-x-2 mt-1 mb-2 pb-2 ">
                                          <span className="w-full p-float-label">
                                            <InputText className="inputClasse" id="email" type="email"  onChange={handleChange} value={state.email} name="email" />
                                            <label htmlFor="email">Email</label>
                                          </span>
                                          <span className="w-full p-float-label">
                                              <Password id="password" className="inputClasse" inputClassName="inputClasse" name="password" onChange={handleChange} value={state.password}  footer={footer} toggleMask/>
                                              <label htmlFor="password">Password</label>
                                          </span>
                                        </div>
                                          <Button label="Sign In" className="loginBtn h-10 font-medium text-sm"/>
                                      </form>
                                      
                                    <Divider/>
                                    <div className="flex align-items-center justify-content-between mb-5 gap-5">
                                            <p className="font-medium no-underline ml-2 text-left cursor-pointer">
                                                <i className="pi pi-angle-double-right" style={{ color: '#708090' }}/> <a className="text-slate-500" href="/login"> Sign in</a>
                                            </p>
                                    </div>
                                  </div>
                              </div>
                          </div>
                      </div>

          </div>
        )
      }
      
                  
        <Toast ref={toast}/>

        <ConfirmDialog/>
    </div>
    );
}
