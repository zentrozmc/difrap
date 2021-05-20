import { Component, OnInit } from '@angular/core';
import { Usuario } from '../../models/usuario';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-consola',
  templateUrl: './consola.component.html',
  styleUrls: ['./consola.component.css'],
  providers:[UsuarioService]
})
export class ConsolaComponent implements OnInit {
  public usuario:Usuario;
  constructor(
    private _usuarioService:UsuarioService
  ) {
    this.usuario = new Usuario();
   }

  ngOnInit(): void {
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

  onSubmit()
  {
    if(this.usuario.comando)
    {
      this._usuarioService.consola(this.usuario).subscribe(
        result=>{
          console.log(result);
        },
        error=>{
          console.log(error);
        }
      );
    }
    else
      alert("el comando no puede estar vacio")
    
  }



}
