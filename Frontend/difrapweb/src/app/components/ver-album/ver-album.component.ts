import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Album } from 'src/app/models/album';
import { AlbumService } from 'src/app/services/album.service';
import { TrackService } from 'src/app/services/track.service';
import { combineLatest } from 'rxjs';
import { Track } from 'src/app/models/track';
import { ReproductorComponent } from '../reproductor/reproductor.component';
import { environment } from 'src/environments/environment';
import { ListarCancionesService } from 'src/app/services/lista-canciones.service';

declare var bootstrap: any;
@Component({
  selector: 'app-ver-album',
  templateUrl: './ver-album.component.html',
  styleUrls: ['./ver-album.component.css'],
  providers: [AlbumService,TrackService]
})

export class VerAlbumComponent implements OnInit {

  @ViewChild("reproductor",null) reproductor:ReproductorComponent;
  @Output() messageEvent = new EventEmitter<string>();
  public filtro:any;
  public album:Album;
  public listaTracks:Array<Track>=[];
  public ambiente;
  constructor(
    private _router: Router,
    private _route:ActivatedRoute,
    private _albumService:AlbumService,
    private _trackService:TrackService,
    private _listaCancionesService:ListarCancionesService

  ) 
  {
    this.ambiente = environment;
    this.album = new Album();
  }
  ngOnInit() 
  {
    combineLatest(
      this._route.params,
      this._route.queryParams,
    ).subscribe(
      results=>{
        this.album.idIncremental = results[0]['id'];
        this.filtro = results[1];
        combineLatest(
          this._albumService.obtener(this.album),
          this._trackService.listar(0,new Track({idAlbum:this.album.idIncremental}))
        )
        .subscribe(
          resultados=>
          {
            this.album = resultados[0] as Album;
            this.listaTracks = resultados[1].entidad;
            this.reproductor.idTrack=-1;
            this.reproductor.stop();
          },
          error=>{
            console.log(error);
            this.album=new Album();
          }
        )
      },
      error=>{}
    );
  }

  play(id)
  {
    this.reproductor.cambiarTrack(id);
  }

  agregarAlReproductor(t:Track)
  {
    if(!this._listaCancionesService.agregarTrack(t))
      alert("El track ya se encuentra en la lista");
  }

}
