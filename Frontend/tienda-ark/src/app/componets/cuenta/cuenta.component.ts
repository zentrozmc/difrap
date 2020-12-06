import { Component, OnInit } from '@angular/core';
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
  constructor(
    private _usuarioService:UsuarioService
  ) { }

  ngOnInit(): void 
  {
    this.usuario = JSON.parse(sessionStorage.getItem('usuario')!);
    this._usuarioService.obtener(this.usuario.usuario).subscribe(
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
    this._usuarioService.actualizarPassword(this.usuario).subscribe(
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
}
