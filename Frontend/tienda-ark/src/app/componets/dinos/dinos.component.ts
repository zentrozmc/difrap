import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuService } from '../../services/menu.service';
import { DinoService} from '../../services/dinos.service';
import { Dino } from '../../models/dino';

@Component({
  selector: 'app-dinos',
  templateUrl: './dinos.component.html',
  styleUrls: ['./dinos.component.css'],
  providers:[DinoService]
})
export class DinosComponent implements OnInit {

  public dino:Dino;
  public buscarDino:Dino;
  public usuario:any;
  public listaDinos:Array<Dino>;
  constructor(
    private _route:ActivatedRoute,
    private _dinosService:DinoService,
    private _menuService:MenuService
  ) 
  {
    this.listaDinos=[];
    this.dino = new Dino();
    this.buscarDino= new Dino();

  }

  ngOnInit(): void {
    this.buscar();
  }

  buscar()
  {
    console.log(this.buscarDino)
    this._dinosService.listar(0,0,this.buscarDino).subscribe(
      result=>
      {
        this.listaDinos=result.entidad;
      }
    );
  }

  ngDoCheck() 
  {    
    this.usuario = sessionStorage.getItem('usuario');
  }
  
  establecerDino(indice:any)
  {
    this.dino = this.listaDinos[indice];
  }

  comprar()
  {
    this._dinosService.comprar(this.dino).subscribe(
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
