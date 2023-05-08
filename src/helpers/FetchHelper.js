import URLHelper from './URLHelper';
export default class FetchHelper{
    static loading=false;

    static getData=async (url)=>{
        FetchHelper.loading=true;
        let token=localStorage.getItem("token");
        const response = await fetch(URLHelper.urlgen(url),
            {
                crossDomain: true,
                method: 'GET',
                headers: { "authorization": token }
            }
        );
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data =await response.json();
        FetchHelper.loading = false;
        return data;    
    }

    static getDataAdmin=async (url)=>{
        FetchHelper.loading=true;
        let token=localStorage.getItem("tokenAdmin");
        const response = await fetch(URLHelper.urlgen(url),
            {
                crossDomain: true,
                method: 'GET',
                headers: { "authorization": token }
            }
        );
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data =await response.json();
        FetchHelper.loading = false;
        return data;    
    }

    static getDataPost = async (url, info) => {
        FetchHelper.loading=true;
        let token=localStorage.getItem("token");
        const response = await fetch(URLHelper.urlgen(url), {
            method: "POST",
            body: info,
            headers: {
                "authorization":token
            }
        })
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data;
        FetchHelper.loading = false;
    }

    static getDataPostAdmin = async (url, info) => {
        let token=localStorage.getItem("tokenAdmin");
        const response = await fetch(URLHelper.urlgen(url), {
            method: "POST",
            body: info,
            headers: {
                "authorization":token
            }
        })
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data;
    }
}
