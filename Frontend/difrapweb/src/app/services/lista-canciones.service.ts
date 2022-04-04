
import { Injectable } from "@angular/core";
import { Track } from "../models/track";
@Injectable()
export class ListarCancionesService 
{
    private listaTracks:Array<Track>;
    private idTrackSonando:Number=-1;
    private posicion:number=-1;
    constructor() 
    {
        this.listaTracks=[];
    }
    public play(track:Track):void
    {
        this.idTrackSonando = track.idIncremental;
        let t = this.listaTracks.find(x=> x.idIncremental == track.idIncremental);
        if(!t)
            this.agregarTrack(track);
    }

    public detener()
    {
        this.idTrackSonando=-1;
    }

    public obtenerIdTrackSonando()
    {
        return this.idTrackSonando;
    }
    public agregarTrack(track:Track) :boolean
    {
        let t = this.listaTracks.find(x=> x.idIncremental == track.idIncremental);
        if(!t)
        {
            this.listaTracks.push(track);
            return true;
        }
        else
            return false;
    }
    public eliminarTrack(track:Track)
    {
        this.listaTracks = this.listaTracks.filter(x=>x.idIncremental!=track.idIncremental);
    }
    public obtenerLista():Array<Track>
    {
        return this.listaTracks;
    }

    public siguienteTrack()
    {
        this.cambiarTrack(1);
    }
    public anteriorTrack()
    {
        this.cambiarTrack(-1);
    } 

    private cambiarTrack(cantidad): void
    {
        if(this.idTrackSonando>0)
        {
            this.listaTracks.forEach((t,i)=>
            {
                if(t.idIncremental == this.idTrackSonando)
                    this.posicion=i;
            })
            let track = this.listaTracks[this.posicion+cantidad];
            if(track)
                this.play(track);
        }
    }



}