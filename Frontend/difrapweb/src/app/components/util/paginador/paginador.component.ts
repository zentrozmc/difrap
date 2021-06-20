import { Component, Input, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-paginador',
  templateUrl: './paginador.component.html',
  styleUrls: ['./paginador.component.css']
})
export class PaginadorComponent implements OnInit {

  @Input() public objeto;
  constructor() { }

  ngOnInit() {
  }

  paginar(pag)
  {
    this.objeto.paginar(pag);
  }

  calcularMinimo()
  {
    let v = environment.regXpag*this.objeto.pagina;
    return Math.min(v,this.objeto.cantidadRegistros);
  }

  agregarDerecha()
  {
    let maxi = Math.min(this.objeto.totalPaginas.length,this.objeto.pagina+2);
    let mini = Math.max(1,this.objeto.pagina-2);
    let dif = maxi-mini;
    let retorno=maxi;
    if(dif<5)
    {
      if(mini==1)
      {
        retorno = Math.min(this.objeto.totalPaginas.length,maxi+(4-dif));
      }
    }
    return retorno;
  }

  agregarIzquierda()
  {
    let maxi = Math.min(this.objeto.totalPaginas.length,this.objeto.pagina+2);
    let mini = Math.max(1,this.objeto.pagina-2);
    let dif = maxi-mini;
    let retorno=mini;
    if(dif<5)
    {
      if(maxi==this.objeto.totalPaginas.length)
      {
        retorno = Math.max(1,mini-(4-dif));
      }
    }
    return retorno;
  }
}
