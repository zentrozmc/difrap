import { Entidad, IEntidad } from "./entidad";
interface IUsuario extends IEntidad
{
    usuario?:String,
    password?:String,
    correo?:String,
    steamId?:String,
    arkId?:String,
    puntos?:Number,
    poder?:Number,
    comando?:String
}
export class Usuario extends Entidad
{
    public usuario:any=null;
    public password:any=null;
    public correo:any=null;
    public steamId:any=null;
    public arkId:any=null;
    public puntos:any=null;
    public poder:any=null;
    public comando:any=null;
    constructor(entidad?:IUsuario)
    {
        super();
        Object.assign(this, entidad);
    }
}