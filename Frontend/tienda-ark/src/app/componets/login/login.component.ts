import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { MenuService } from 'src/app/services/menu.service';
import { UsuarioService } from 'src/app/services/usuario.service';
declare var $:any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers:[UsuarioService]
})
export class LoginComponent implements OnInit {
  public usuario:any = null;
  public inicioSesion:Usuario;
  public registro:Usuario;
  public olvido:Usuario;
  public sPassword:any;
  constructor(
    private _usuarioService:UsuarioService,
    private _menuService:MenuService
  ) 
  {
    this.inicioSesion = new Usuario(null,null,null,null,null,null,null);
    this.registro = new Usuario(null,null,null,null,null,null,null);
    this.olvido = new Usuario(null,null,null,null,null,null,null);
  }

  ngDoCheck() 
  {    
    this.usuario = sessionStorage.getItem('usuario');
  }

  ngOnInit(): void 
  {
    $(function () {
      $('[data-toggle="popover"]').popover()
    });
    
  }

  login()
  {
    this._usuarioService.login(this.inicioSesion).subscribe(
      result=>
      {
          let token = result.headers?.get("Authorization");
          sessionStorage.setItem("usuario",JSON.stringify(this.inicioSesion));
          sessionStorage.setItem("token",token ? token : "");
          localStorage.setItem("usuario",JSON.stringify(this.inicioSesion));
          localStorage.setItem("token",token ? token : "");
          this._menuService.getMenu().crearIntervalo();
      },
      error=>
      {
        alert("Usuario o Contraseña Incorrectos");
        console.log("Error",error);
      }
    )
  }
  
  registrar()
  {
    this._usuarioService.agregar(this.registro).subscribe(
      result=>
      {
        alert(result.descripcion);
        this.registro = new Usuario(null,null,null,null,null,null,null);
      },
      error=>
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    )
  }

  recuperar()
  {
    this._usuarioService.recuperarPassword(this.olvido).subscribe(
      result=>
      {
        alert(result.descripcion);
        this.olvido = new Usuario(null,null,null,null,null,null,null);
      },
      error=>
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    )
  }

}
