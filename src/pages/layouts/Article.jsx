import style from './../../assets/css/article.module.css';
export default function Article(props){
    return(
            
    

    
      

        <div class="w-full max-w-full mb-8 sm:w-1/2 px-4 lg:w-1/3 flex flex-col">
          <img
            src={`data:image/png;base64,${props.article.photo}`}
            alt={props.article.title}
            class="object-cover object-center w-full h-48"
          />
          <div class="flex flex-grow">
            <div class="triangle"></div>
            <div
              class="flex flex-col w-full justify-between px-4 py-6 bg-gray-700 border border-gray-800 "
            >
              <div>
                <h2
                  href="#"
                  class="inline-block mb-4 text-xs font-bold capitalize border-b-2 border-purple-600 hover:text-purple-600"
                  >{props.article.type} {props.article.date}</h2>
                <h1
                  href="#"
                  class="block mb-4 text-2xl font-black leading-tight hover:underline hover:text-purple-600"
                >
                  {props.article.title}
                </h1>
                <p class="mb-4">
                    {props.article.description}
                </p>
            </div>
            <div>
              <a href="#" class="inline-block pb-1 mt-2 text-base font-black text-purple-600 uppercase border-b border-transparent hover:border-purple-600">Read More -></a
                >
              </div>
            </div>
          </div>
        </div>
    );
}