import { useEffect } from "react";
import FetchHelper from "../../helpers/FetchHelper";
export default function Search(props) {



    const getAllDomaine=()=>{
        const fetch=FetchHelper.getData("domaine/getAll");
        var listTemp;
        fetch.then(response=>{
            listTemp=[...response];
            return listTemp;
        })
        .catch(error=>console.log(error));
    }



    return(
        <div>
                <div className="rounded-lg p-14">
                    <form>
                            <div className="sm:flex items-center bg-slate-800 rounded-lg overflow-hidden px-2 py-1 justify-between">
                                <input className="bg-slate-800 text-base text-gray-400 flex-grow outline-none px-2 " type="text" placeholder="Search" />
                                <div className="ms:flex items-center px-2 rounded-lg space-x-4 mx-auto ">
                                    <select id="Com" className="bg-slate-800 text-base text-gray-400 outline-none  px-4 py-2 rounded-lg">
                                    </select>
                                    <button className="bg-slate-500 text-white text-base rounded-lg px-4 py-2 font-thin">Search</button>
                                </div>
                            </div>
                    </form>
                </div>
        </div>
    );
}