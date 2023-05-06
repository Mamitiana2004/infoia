import style from './../../assets/css/loading.module.css';
export function Loading() {
    return(
        <div className={style.body}>
            <div className={style.scene}>
                <div className={style.shadow}></div>
                <div className={style.jumper}>
                    <div className={style.spinner}>
                        <div className={style.scaler}>
                            <div className={style.loader}>
                                <div className={style.cuboid}>
                                    <div className={style.cuboid_side}></div>
                                    <div className={style.cuboid_side}></div>
                                    <div className={style.cuboid_side}></div>
                                    <div className={style.cuboid_side}></div>
                                    <div className={style.cuboid_side}></div>
                                    <div className={style.cuboid_side}></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
    );
}