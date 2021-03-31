import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { ItemService } from 'src/app/services/item.service';
import { MenuService } from 'src/app/services/menu.service';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css'],
  providers:[ItemService]
})
export class ItemsComponent implements OnInit {

  public listaItems:any;
  public usuario:any;
  constructor(
    private _route:ActivatedRoute,
    private _itemService:ItemService,
    private _menuService:MenuService
  ) 
  {

  }

  ngOnInit(): void 
  {


    this._route.params.forEach(
      (params:Params) => 
      {
        let id = params['id'];
        if(id)
        {
          this._itemService.listar(id).subscribe(
            result=>
            {
              this.listaItems=result.entidad;
            },
            error=>{
              alert("ha ocurrido un error al listar items intenta mas tarde");
              console.log(error);
            }
          );
        }
        else
        {
          alert("Url Invalida");
        }
        
      });

    
  }
  ngDoCheck() 
  {    
    this.usuario = sessionStorage.getItem('usuario');
  }
  comprar(id:any){
    this._itemService.comprar(id).subscribe(
      result=>
      {
        alert(result.descripcion);
        this._menuService.getMenu().actualizarUsuario();
      },
      error=>
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    );
  }
}