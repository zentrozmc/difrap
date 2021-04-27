import { Entidad, IEntidad } from "./entidad";

interface IDino extends IEntidad
{
    nombre?:String,
    nivel?:Number,
    comando?:String,
    precio?:Number,
    icono?:String
}
export class Dino extends Entidad
{
    public idIncremental:any=null;
    public nombre:any=null;
    public nivel:any=null;
    public comando:any=null;
    public precio:any=null;
    public icono:any=null;
    constructor(entidad?:IDino)
    {
        super();
        Object.assign(this, entidad);
    }
}