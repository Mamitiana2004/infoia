import { useState } from "react";
import { useSearchParams } from "react-router-dom";
import FetchHelper from "../../helpers/FetchHelper";

export default function Search(props) {

    const [search,setSearch]=useState({
        s:"",
        d:"0"
    });

    const handleChange=(event)=>{
        const { name, value } = event.target;
        setSearch(prevState => ({ ...prevState, [name]: value }));
    }

    // const handleSubmit=(event)=>{
    //     event.preventDefault();
    //     var donne=new useSearchParams();
    //     donne.append("s",search.s);
    //     donne.append("d",search.d);
        
    //     const fetch=FetchHelper.getDataPost("article/search",donne);
    //     fetch.then(response=>{
    //         console.log(response);
    //     }).catch(error=>console.log(error));
        
    // }



    return(
        <div>    
            <form >
                            <div className="sm:flex items-center bg-slate-800 rounded-lg overflow-hidden px-2 py-1 justify-between">
                                <input name="s" required onChange={handleChange} value={search.s} className="bg-slate-800 text-base text-gray-400 flex-grow outline-none px-2 " type="text" placeholder="Search" />
                                <div className="ms:flex items-center px-2 rounded-lg space-x-4 mx-auto ">
                                    <select name="d" required onChange={handleChange} value={search.d} id="Com" className="bg-slate-800 text-base text-gray-400 outline-none  px-4 py-2 rounded-lg">
                                        <option value="0">All domaine</option>
                                        {props.domaine.map(d=>{
                                            return <option value={d.id}>{d.type}</option>
                                        })}
                                    </select>
                                    <button type="submit" className="bg-slate-500 text-white text-base rounded-lg px-4 py-2 font-thin">Search</button>
                                </div>
                            </div>
                </form>
        </div>
    );
}