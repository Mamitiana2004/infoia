export default function Pagination(props) {

    const pageN=()=>{
        const page=[];
        for (let i = 1; i <= props.nbrPage; i++) {
            if(props.pageActive!=i){
                page.push(<a href={"/acceuil/"+i} className="text-sm font-medium leading-none cursor-pointer text-gray-600 hover:text-indigo-700 border-t border-transparent hover:border-indigo-400 pt-3 mr-4 px-2">{i}</a>);
            }
            else{
                page.push(<p className="text-sm font-medium leading-none cursor-pointer text-indigo-700 border-t border-indigo-400 pt-3 mr-4 px-2">{i}</p>);
            }
        }
        return <>{page}</>;
    }    

    const pageNext=()=>{
        if(props.pageActive>=props.nbrPage){
            return <></>;
        }
        else{
            return (
                <>
                    <a href={"/acceuil/"+(props.pageActive-(-1))} className="text-sm font-medium leading-none mr-3">Next</a>
                    <i className="pi pi-arrow-right"/>
                </>
            );
        }
    }

    const pagePrev=()=>{
        if(props.pageActive<=1){
            return <></>;
        }
        else{
            return (
                <>
                    <i className="pi pi-arrow-left"/>
                    <a href={"/acceuil/"+(props.pageActive-1)} className="text-sm ml-3 font-medium leading-none ">Previous</a>                 
                </>
            );
        }
    }




    return(
        <div className="flex items-center justify-center py-10 lg:px-0 sm:px-6 px-4">

                    <div className="lg:w-3/5 w-full  flex items-center justify-between border-t border-gray-200">
                        <div className="flex items-center pt-3 text-gray-600 hover:text-indigo-700 cursor-pointer">
                            {pagePrev()}
                        </div>
                        <div className="sm:flex hidden text-white">
                            {pageN()}
                        </div>
                        <div className="flex items-center pt-3 text-gray-600 hover:text-indigo-700 cursor-pointer">
                            {pageNext()}
                        </div>
                    </div>
        </div>
    );
}   