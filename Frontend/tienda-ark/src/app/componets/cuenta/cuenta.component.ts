import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-cuenta',
  templateUrl: './cuenta.component.html',
  styleUrls: ['./cuenta.component.css'],
  providers:[UsuarioService]
})
export class CuentaComponent implements OnInit {
  public usuario:any;
  public usuarioS:any;
  public usuarioCP:any=null;
  constructor(
    private _usuarioService:UsuarioService
  ) { 
    this.usuarioCP = new Usuario();
  }

  ngOnInit(): void 
  {
    this.usuario = JSON.parse(sessionStorage.getItem('usuario')!);
    this._usuarioService.obtener(this.usuario).subscribe(
      result => 
      { 
        this.usuario = result;
      },
      error => 
      {
        console.log(<any> error);
      }
    );
   }

  ngDoCheck() 
  {    
    this.usuarioS = sessionStorage.getItem('usuario');
  }
  guardarCambios()
  {
    this._usuarioService.actualizarCuenta(this.usuario).subscribe(
      result => 
      { 
        alert(result.descripcion);
      },
      error => 
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    );
  }

  cambiarPassword()
  {
    this.usuarioCP.usuario = this.usuario.usuario;
    this._usuarioService.actualizarPassword(this.usuarioCP).subscribe(
      result => 
      { 
        alert(result.descripcion);
        this.usuarioCP = new Usuario();
      },
      error => 
      {
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
      }
    );
  }
}
