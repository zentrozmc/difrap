import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { AnuncioService } from 'src/app/services/anuncio.service';
import { MenuService } from 'src/app/services/menu.service';

@Component({
  selector: 'app-cobrar-anuncio',
  templateUrl: './cobrar-anuncio.component.html',
  styleUrls: ['./cobrar-anuncio.component.css'],
  providers:[AnuncioService]
})
export class CobrarAnuncioComponent implements OnInit {

  public anuncio:any;
  
  constructor(
    private _route:ActivatedRoute,
    private _anuncioService:AnuncioService,
    private _menuService:MenuService
  ) { }


  ngOnInit(): void {
    this._route.params.forEach(
      (params:Params) => 
      {
        let id = params['id'];
        this._anuncioService.cobrar(id).subscribe(
          result=>
          {
            this._anuncioService.obtener(id).subscribe(
              result=>{
                this.anuncio= result;
                this._menuService.getMenu().actualizarUsuario();
              },
              error=>{}
            );
          },
          error=>{
            let obj = JSON.parse(error._body);
            alert(obj.descripcion);
          }
        )
      });
  }

}
