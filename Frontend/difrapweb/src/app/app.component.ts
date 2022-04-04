import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ReproductorComponent } from './components/reproductor/reproductor.component';
import { Track } from './models/track';
import { ListarCancionesService } from './services/lista-canciones.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers:[ListarCancionesService]
})
export class AppComponent {
  @ViewChild("reproductor",null) reproductor:ReproductorComponent;
  title = 'difrapweb';


  public listaTracks:Array<Track>=[];
  constructor(private _listarCancionesService:ListarCancionesService)
  {
    this.listaTracks = this._listarCancionesService.obtenerLista();
    
  }
  reproducir(t:Track,i)
  {
    this._listarCancionesService.play(t);
    this.reproductor.cambiarTrack(i);
  }


}
