export interface ITrack 
{
    idIncremental?:Number,
    idAlbum?:Number,
    nombre?:String,
    diracion?:String,
    drive?:String
}
export class Track 
{
    public idIncremental?:Number=null;
    public idAlbum?:Number=null;
    public nombre?:String=null;
    public diracion?:String=null;
    public drive?:String=null;
    constructor(entidad?:ITrack)
    {
        Object.assign(this,entidad);
    }
}