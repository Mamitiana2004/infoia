import style from './../../assets/css/404.module.css'
export default function Error404() {
    return(
        <div className={style.body}>
            <div className={style.notfound_bg}></div>
            <div className={style.notfound}>
                <div className={style.notfound_404}>
                    <h1>404</h1>
                </div>
                <h2>we are sorry, but the page you requested was not found</h2>
                <a href="/" className={style.home_btn}>Go Home</a>
            </div>
        </div>
    );
}