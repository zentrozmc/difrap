import { Entidad, IEntidad } from "./entidad";
interface IAnuncio extends IEntidad
{
    url?:String,
    fechaUltimoUso?:Date,
    fechaActivacion?:Date,
    valor?:Number,
    estado?:Number
}
export class Anuncio extends Entidad
{
    public url:any=null;
    public fechaUltimoUso:any=null;
    public fechaActivacion:any=null;
    public valor:any=null;
    public estado:any=null;
    constructor(entidad?:IAnuncio)
    {
        super();
        Object.assign(this, entidad);
    }
}