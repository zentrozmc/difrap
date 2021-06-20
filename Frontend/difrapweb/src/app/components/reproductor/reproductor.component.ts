import { analyzeAndValidateNgModules } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { AudioService } from 'src/app/services/audio.service';

@Component({
  selector: 'app-reproductor',
  templateUrl: './reproductor.component.html',
  styleUrls: ['./reproductor.component.css'],
  providers:[AudioService]
})
export class ReproductorComponent implements OnInit {

  public id:String=null;

  public duracion:number=0;
  public duracionTotal:number=0;
  public tiempo;
  public tiempoTotal;
  public audio:HTMLAudioElement;
  constructor(
	  private _audioService:AudioService
  ) { }

  ngOnInit(): void {
    this.cargarReproductor();
	this._audioService.getTimeRemaining().subscribe(
		result=>{
			//this.tiempo=result;
		}
	);
	this._audioService.getPlayerStatus().subscribe(
		result=>{
			console.log("getPlayerStatus",result);
		}
	);
	this._audioService.getPercentElapsed().subscribe(
		result=>{
			console.log("getPercentElapsed",result);
		}
	);
	this._audioService.getPercentLoaded().subscribe(
		result=>{
			console.log("getPercentLoaded",result);
		}
	);
	this._audioService.getTimeElapsed().subscribe(
		result=>{
			this.tiempo=result;
		}
	);
  }

  play(id)
  {
	  
	this.audio = document.getElementById("reproductor") as HTMLAudioElement;
	this.audio.play();
	this.audio.addEventListener("timeupdate",this.mover);
	this.duracionTotal = this.audio.duration;
	this._audioService.toggleAudio();
	
  }

  posicionar()
  {
	this.audio.currentTime = this.duracion;
	console.log("duracion",this.duracion);
  }

  
  private mover =(evt) =>
  {
	this.duracion=this.audio.currentTime;
	console.log("audio.currentTime",this.audio.currentTime);
	console.log("audio.duration",this.audio.duration);
	console.log("audio.ended",this.audio.ended);
	console.log("audio.muted",this.audio.muted);
  }
  


   cambiarTrack(track) 
   {
    let viejo_audio = document.getElementById("reproductor")
    let audio_padre = viejo_audio.parentNode
    audio_padre.removeChild(viejo_audio)
    let nuevo_audio = document.createElement("audio")
    nuevo_audio.setAttribute("id","reproductor")
    nuevo_audio.setAttribute("controls", "controls")
    nuevo_audio.setAttribute("autoplay", "autoplay")
    nuevo_audio.setAttribute("preload", "auto")
    let source = document.createElement("source")
    source.setAttribute("src", "http://difrap.cl:8080/apiDifRap/track/play/"+track)
    source.setAttribute("type", "audio/mpeg")
    source.setAttribute("id", "reproductorSource")
    nuevo_audio.appendChild(source)
    audio_padre.appendChild(nuevo_audio)


	//this._audioService.setAudio("http://difrap.cl:8080/apiDifRap/track/play/"+track);
  }

  cargarReproductor() 
  {
    let nuevo_audio = document.createElement("audio")
    nuevo_audio.setAttribute("id","reproductor")
    nuevo_audio.setAttribute("controls", "controls")     
	nuevo_audio.setAttribute("autoplay", "autoplay")  
    let source = document.createElement("source")
    source.setAttribute("src", "")
    source.setAttribute("type", "audio/mpeg")
    source.setAttribute("id", "reproductorSource")
    nuevo_audio.appendChild(source)
    let padre = document.getElementById("reproductorBox")
    padre.appendChild(nuevo_audio)
 }
}
