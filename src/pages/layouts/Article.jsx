export default function Article(props){
    return(
        <div className="w-full p-6 h-auto shadow-2xl rounded-lg mb-4 flex-shrink-0 flex-grow bg-gray-800 text-white">
                        <div>
                            <h1 className="text-lg font-semibold text-slate-100">{props.article.title}</h1>
                            <p className="text-sm text-slate-500">
                                {props.article.type}
                            </p>
                        </div>
                        <h3 className="text-sm font-medium text-slate-400 mt-2">
                            {props.article.description}
                        </h3>
                        <p className="text-sm mt-4 text-slate-500">
                                {props.article.datePub}
                        </p>
                        <br/>
                        <a href={`/article/${props.article.type}-${props.article.id}`}>
                            <button className="h-10 px-6 font-semibold rounded-full border hover:text-gray-800 hover:bg-white border-slate-200 text-slate-100" type="button">
                                Voir plus
                            </button>
                        </a>

        </div>
    );
}