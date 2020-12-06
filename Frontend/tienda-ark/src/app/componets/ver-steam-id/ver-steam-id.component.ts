import { Component, OnInit } from '@angular/core';
import { Player } from 'src/app/models/player';
import { PlayerService } from 'src/app/services/player.service';

@Component({
  selector: 'app-ver-steam-id',
  templateUrl: './ver-steam-id.component.html',
  styleUrls: ['./ver-steam-id.component.css'],
  providers:[PlayerService]
})
export class VerSteamIdComponent implements OnInit {

  public listaPlayer:Array<Player>;
  constructor(
    private _playerService:PlayerService
  ) { 
    this.listaPlayer=[];
  }

  ngOnInit(): void {
    this._playerService.listar().subscribe(
      result=>
      {
        this.listaPlayer=result.resultado;
      },
      error=>{
        alert("ha ocurrido un error al buscar players conectados")
        console.log("Error",error);
      }
    );
  }

}
