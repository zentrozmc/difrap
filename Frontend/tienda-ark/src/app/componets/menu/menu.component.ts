import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Item } from 'src/app/models/item';
import { ItemService } from 'src/app/services/item.service';
import { UsuarioService } from 'src/app/services/usuario.service';
declare var $:any;

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
  providers:[UsuarioService,ItemService]
})
export class MenuComponent implements OnInit {
  public usuario:any = null;
  public idIntervalo:any;

  constructor(
    private _usuarioService:UsuarioService,
    private _itemService:ItemService,
    private router:Router
  ) 
  { 
  }

  ngAfterViewChecked()
  {
    $('[data-toggle="popover"]').popover();
  }
  
  crearIntervalo()
  {
    if(!this.idIntervalo)
    {
      this.idIntervalo = setInterval(() => 
      {
        this._usuarioService.refresca_token().subscribe(
          result => 
          { 
            let token = result.token;
            localStorage.setItem("token",token);
            sessionStorage.setItem("token",token);
          },
          error => 
          {
            console.log(<any> error);
            this.logout();
          }
        );
      }, 120000);
    }
  }
  ngDoCheck() 
  {    
    this.cargarUsuario();
  }

  actualizarUsuario()
  {
    this._usuarioService.obtener(this.usuario).subscribe(
      result => 
      { 
        this.usuario = result;
      },
      error => 
      {
        console.log(<any> error);
        this.logout();
      }
    );
  }

  ngOnInit(): void {
    this.cargarUsuario();
  }

  cargarUsuario()
  {
    if(localStorage.getItem("usuario"))
    {
     
      let token = localStorage.getItem('token');
      sessionStorage.setItem("usuario",localStorage.getItem('usuario')!);
      sessionStorage.setItem("token",token?token:"");
      if(!this.usuario)
      {
        this.usuario = JSON.parse(localStorage.getItem('usuario')!.toString());
        this.actualizarUsuario();
      } 
    }
    else if(sessionStorage.getItem("usuario"))
    {
      let token = sessionStorage.getItem('token');
      localStorage.setItem("usuario",sessionStorage.getItem('usuario')!);
      localStorage.setItem("token",token?token:"");
      if(!this.usuario)
      {
        this.usuario = JSON.parse(sessionStorage.getItem('usuario')!.toString());
        this.actualizarUsuario();
      } 
    }
  }

  logout()
  {
    if (this.idIntervalo) {
      clearInterval(this.idIntervalo);
      console.log("finalizando intervalo",this.idIntervalo);
      this.idIntervalo = null;
     
    } 
    localStorage.clear();
    sessionStorage.clear();
    this.usuario = null;
    this.router.navigate(['']);
   
  }

  gacha()
  {
    this._itemService.gacha().subscribe(
      result=>
      {
        let item:Item = result.resultado;
        alert("Has recibido "+item.cantidad+" "+item.nombre);
      },
      error=>
      {
        alert(error.error.descripcion);
      }
    );
  }
}
