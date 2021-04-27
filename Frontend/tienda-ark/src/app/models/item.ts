import { Entidad,IEntidad } from "./entidad";

interface IItem extends IEntidad
{
    nombre?:String,
    cantidad?:Number,
    comando?:String,
    precio?:Number,
    icono?:String,
    tipo?:Number
}
export class Item extends Entidad
{
    public idIncremental:any=null;
    public nombre:any=null;
    public cantidad:any=null;
    public comando:any=null;
    public precio:any=null;
    public icono:any=null;
    public tipo:any=null;
    constructor(entidad?:IItem)
    {
        super();
        Object.assign(this, entidad);
    }
}