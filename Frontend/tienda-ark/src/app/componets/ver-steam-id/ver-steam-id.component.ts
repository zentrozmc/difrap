import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Player } from 'src/app/models/player';
import { PlayerService } from 'src/app/services/player.service';


@Component({
  selector: 'app-ver-steam-id',
  templateUrl: './ver-steam-id.component.html',
  styleUrls: ['./ver-steam-id.component.css'],
  providers:[PlayerService]
})
export class VerSteamIdComponent implements OnInit {

  public mostrarCarga:any;
  public listaPlayer:Array<Player>;
  constructor(
    private _playerService:PlayerService,
    private _spinner: NgxSpinnerService
  ) 
  { 
    this.listaPlayer=[];
  }

  ngOnInit(): void {
    this._spinner.show();
    this._playerService.listar().subscribe(
      result=>
      {
        this.listaPlayer=result.resultado;
      },
      error=>{
        let obj = JSON.parse(error._body);
        alert(obj.descripcion);
        
      },
      ()=>{this._spinner.hide();}
    );
  }

}
