export interface IAlbum 
{
    idIncremental?:number,
    album?:String,
    artista?:String,
    anho?:Number,
    link?:String,
    youtube?:String,
    drive?:String
}
export class Album 
{
    public idIncremental:number=null;
    public album:String=null;
    public artista:String=null;
    public anho:Number=null;
    public link:String=null;
    public youtube:String=null;
    public drive:String=null;
    constructor(
       entidad?:IAlbum
    )
    {
        Object.assign(this,entidad);
    }
}