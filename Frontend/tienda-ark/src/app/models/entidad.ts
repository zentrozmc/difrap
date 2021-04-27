export interface IEntidad
{
    idIncremental?:Number; 
}
export abstract class Entidad
{
    public idIncremental=null;
    constructor(entidad?:IEntidad)
    {
        Object.assign(this, entidad);
    }
}