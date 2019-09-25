import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Album } from 'src/app/models/album';
import { AlbumService } from 'src/app/services/album.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';


@Component({
  selector: 'app-ver-album',
  templateUrl: './ver-album.component.html',
  styleUrls: ['./ver-album.component.css'],
  providers: [AlbumService]
})
export class VerAlbumComponent implements OnInit {

  public album:Album;
  constructor(
    private _router: Router,
    private _route:ActivatedRoute,
    private _albumService:AlbumService,
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
    this._route.params.forEach(
      (params:Params) => 
      {
        this.album.idIncremental = params['id'];
        this._albumService.obtener(this.album).subscribe(
          (result:Album)=>
          {
            this.album = result;
          },
          error=>{
            console.log(error);
          }
        )
      });
  }

}
