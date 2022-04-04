import { Component, Input, OnInit } from '@angular/core';
import { Track } from 'src/app/models/track';
import { AudioContexService } from 'src/app/services/audio-context.service';
import { AudioService } from 'src/app/services/audio.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-reproductor',
  templateUrl: './reproductor.component.html',
  styleUrls: ['./reproductor.component.css'],
  providers:[AudioService,AudioContexService]
})
export class ReproductorComponent implements OnInit {

  @Input() public listaTrack:Array<Track>=[];
  public idTrack=-1;
  public duracion:number=0;
  public duracionTotal:number=0;
  public carga:number=0;
  public tiempo;
  public estado="loading";
  public audio:HTMLAudioElement;
  public volumen:number=1;
  constructor(
	  private _audioService:AudioService
  ) { }

  ngOnInit(): void 
  {
	this._audioService.getTimeRemaining().subscribe(
		result=>{
			//this.tiempo=result;
		}
	);
	this._audioService.getPlayerStatus().subscribe(
		result=>{
      this.estado = result;
      if(this.estado=="ended")
        this.siguiente();
		}
	);
	this._audioService.getDuracion().subscribe(
		result=>{
      this.duracionTotal=this._audioService.getAudio().duration;
      this.duracion = result;
		}
	);
	this._audioService.getPercentLoaded().subscribe(
		result=>{
      this.carga=result;
		}
	);
	this._audioService.getTimeElapsed().subscribe(
		result=>{
			this.tiempo=result;
		}
	);
  }

  ngOnDestroy(){
    this._audioService.setAudio(null);
  }

  play()
  {
    if(this.idTrack>=0)
	    this._audioService.toggleAudio();
    else
      this.cambiarTrack(0);
  }

  stop()
  {
    this._audioService.pauseAudio();
  }

  siguiente()
  {
    this.cambiarTrack(this.idTrack+1);
  }

  anterior()
  {
    this.cambiarTrack(this.idTrack-1);
  }
  posicionar()
  {
	  this._audioService.seekAudio(this.duracion);
  }

  cambiarTrack(track) 
  { 
    if(track>-1 && track<this.listaTrack.length)
    {
      this.idTrack=track;
      this.carga=0;
      this._audioService.setAudio(environment.apiDifRap+"track/play/"+this.listaTrack[track].drive);
      this.volumen=this._audioService.getAudio().volume;
    }
  }

  silenciar()
  {
    if(this._audioService.getAudio().muted)
      this._audioService.getAudio().muted=false;
    else
      this._audioService.getAudio().muted=true;
  }

  esMuted()
  {
    return this._audioService.getAudio().muted;
  }

  setVolumen(tipo)
  {
    let v =this._audioService.getAudio().volume;
    if(tipo==0)
      v = v- 0.1;
    else
      v = v + 0.1;
    this._audioService.getAudio().volume = v;
    this.volumen=v;
  }
}
