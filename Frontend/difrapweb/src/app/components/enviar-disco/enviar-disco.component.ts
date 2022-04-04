import { Component, OnInit } from '@angular/core';
import { EnviarDisco } from 'src/app/models/enviar-disco';
import { Album } from 'src/app/models/album';
import { EnviarDiscoService } from 'src/app/services/enviar-disco.service';

@Component({
  selector: 'app-enviar-disco',
  templateUrl: './enviar-disco.component.html',
  styleUrls: ['./enviar-disco.component.css'],
  providers: [EnviarDiscoService]
})
export class EnviarDiscoComponent implements OnInit {

  public disco:EnviarDisco;
  constructor(
    private _enviarDiscoService:EnviarDiscoService
  ) {
    let album = new Album();
    this.disco = new EnviarDisco(album,null);
   }

  ngOnInit() {
  }

  onSubmit()
  {
    this._enviarDiscoService.enviar(this.disco).subscribe(
      result=>{
        if(result)
          alert("Mensaje enviado Correctamente");
        else
          alert("Ha ocurrido un error al enviar el mensaje, intentelo mas tarde");
          
        let album = new Album();
        this.disco = new EnviarDisco(album,null);
      },
      error=>{
        alert("Ha ocurrido un error al enviar el mensaje, intentelo mas tarde");
        console.log(error);
      }
    );
    
  }

}
