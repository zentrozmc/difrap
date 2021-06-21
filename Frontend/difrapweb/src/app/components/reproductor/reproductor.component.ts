import { analyzeAndValidateNgModules } from '@angular/compiler';
import { Component, Input, OnInit } from '@angular/core';
import { Track } from 'src/app/models/track';
import { AudioService } from 'src/app/services/audio.service';

@Component({
  selector: 'app-reproductor',
  templateUrl: './reproductor.component.html',
  styleUrls: ['./reproductor.component.css'],
  providers:[AudioService]
})
export class ReproductorComponent implements OnInit {

  @Input() public listaTrack:Array<Track>=[];
  public duracion:number=0;
  public duracionTotal:number=0;
  public carga:number;
  public tiempo;
  public tiempoTotal;
  public estado="loading";
  public audio:HTMLAudioElement;
  constructor(
	  private _audioService:AudioService
  ) { }

  ngOnInit(): void {
	this._audioService.getTimeRemaining().subscribe(
		result=>{
			//this.tiempo=result;
		}
	);
	this._audioService.getPlayerStatus().subscribe(
		result=>{
      this.estado = result;
		}
	);
	this._audioService.getPercentElapsed().subscribe(
		result=>{
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

  play(id)
  {
    if(this._audioService.getAudio().src)
	    this._audioService.toggleAudio();
    else
      this.cambiarTrack(0);
  }

  posicionar()
  {
	  this.audio.currentTime = this.duracion;
	  console.log("duracion",this.duracion);
  }

  cambiarTrack(track) 
  { 
    if(track>-1)
    {
      this._audioService.setAudio("http://difrap.cl:8080/apiDifRap/track/play/"+this.listaTrack[track].drive);
    }
  }
}
