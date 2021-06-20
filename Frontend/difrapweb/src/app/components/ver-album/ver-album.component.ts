import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Album } from 'src/app/models/album';
import { AlbumService } from 'src/app/services/album.service';
import { DomSanitizer } from '@angular/platform-browser';
import { TrackService } from 'src/app/services/track.service';
import { combineLatest } from 'rxjs';
import { Track } from 'src/app/models/track';
import { ReproductorComponent } from '../reproductor/reproductor.component';
import { cpuUsage } from 'process';


@Component({
  selector: 'app-ver-album',
  templateUrl: './ver-album.component.html',
  styleUrls: ['./ver-album.component.css'],
  providers: [AlbumService,TrackService]
})
export class VerAlbumComponent implements OnInit {

  @ViewChild("reproductor",null) reproductor:ReproductorComponent;
  public filtro:any;
  public album:Album;
  public listaTracks:Array<Track>=[];
  constructor(
    private _router: Router,
    private _route:ActivatedRoute,
    private _albumService:AlbumService,
    private _trackService:TrackService,
    private _sanitizer: DomSanitizer

  ) 
  {
    this.album = new Album(null,null,null,null,null,null);
  }

  getVideoIframe(url) {
    var video, results;
    if (url === null) {
        return '';
    }
    results = url.match('[\\?&]v=([^&#]*)');
    video = (results === null) ? url : results[1];
    return this._sanitizer.bypassSecurityTrustResourceUrl('https://www.youtube.com/embed/'+video);   
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
          },
          error=>{
            console.log(error);
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
}
