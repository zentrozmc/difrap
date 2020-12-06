import { Component, OnInit } from '@angular/core';
import { Anuncio } from 'src/app/models/anuncio';
import { AnuncioService } from 'src/app/services/anuncio.service';

@Component({
  selector: 'app-anuncios',
  templateUrl: './anuncios.component.html',
  styleUrls: ['./anuncios.component.css'],
  providers:[AnuncioService]
})
export class AnunciosComponent implements OnInit {

  public listaAnuncios:Array<Anuncio>;
  public usuario:any;
  constructor(
    private _anuncioService:AnuncioService
  ) 
  {
    this.listaAnuncios=[];
  }

  ngOnInit(): void 
  {
    this._anuncioService.listar().subscribe(
      result=>{
        this.listaAnuncios=result.entidad;
      },
      error=>{
        alert("ha ocurrido un error al listar anuncios intenta mas tarde");
        console.log(error);
      }
    );
  }
  activar(id:any,url:any)
  {
    this._anuncioService.activar(id).subscribe(
      result=>
      {
        window.open(url,"_self");
      },
      error=>
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    );
  }
  
  ngDoCheck() 
  {    
    this.usuario = sessionStorage.getItem('usuario');
  }
}
