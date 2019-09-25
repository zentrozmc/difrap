import { Component, OnInit } from '@angular/core';
import { Album } from 'src/app/models/album';
import { AlbumService } from 'src/app/services/album.service';
import { environment } from 'src/environments/environment';
import { TouchSequence } from 'selenium-webdriver';
import { Paginacion } from 'src/app/models/paginacion';

@Component({
  selector: 'app-albumes',
  templateUrl: './albumes.component.html',
  styleUrls: ['./albumes.component.css'],
  providers: [ AlbumService ]
})
export class AlbumesComponent implements OnInit {

  public album:Album;
  public albumtmp:Album;
  public cantidadRegistros:number;
  public totalPaginas:Array<any>;
  public listaAlbumes:Array<Album>;
  public pagina:number;

  constructor( private _albumService:AlbumService) {
    this.album = new Album(null,null,null,null,null,null);
    this.cantidadRegistros = null;
    this.listaAlbumes= [];
    this.totalPaginas= [];
    this.pagina=1;
   }

  ngOnInit() {
  }

  limpiarFiltros(){
    this.album = new Album(null,null,null,null,null,null);
  }

  onSubmit()
  {
    this.pagina=1;
    this.listarAlbumes(this.album);
    this.albumtmp=this.album;
  }

  paginar(pag)
  {
    this.pagina=pag;
    this.listarAlbumes(this.albumtmp);
  }

  listarAlbumes(album:Album)
  {
    this._albumService.listar(this.pagina,album).subscribe(
      (result:any) =>{
        this.listaAlbumes = result.entidad;
        this.totalPaginas = Array(Math.ceil(result.paginacion.cantidadRegistros/environment.regXpag)).fill(5);
      },
      error=>{
        console.log(error);
      }
    );
  }
}
