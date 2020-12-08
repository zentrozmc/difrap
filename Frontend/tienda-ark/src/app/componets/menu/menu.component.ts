import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
  providers:[UsuarioService]
})
export class MenuComponent implements OnInit {
  public usuario:any = null;
  public idIntervalo:any;

  constructor(
    private _usuarioService:UsuarioService,
    private router:Router
  ) 
  { 
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
            sessionStorage.setItem("token",token);
            localStorage.setItem("token",token);
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
    this._usuarioService.obtener(this.usuario.usuario).subscribe(
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
      if(!this.usuario)
      {
        this.usuario = JSON.parse(localStorage.getItem('usuario')!.toString());
        this.actualizarUsuario();
      } 
      let token = localStorage.getItem('token');
      sessionStorage.setItem("usuario",localStorage.getItem('usuario')!);
      sessionStorage.setItem("token",token?token:"");
      
    }
    else if(sessionStorage.getItem("usuario"))
    {
      this.usuario = sessionStorage.getItem('usuario');
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

}
