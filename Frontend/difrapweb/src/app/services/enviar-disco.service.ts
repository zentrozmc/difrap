
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams, } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Album } from '../models/album';
import { EnviarDisco } from '../models/enviar-disco';

@Injectable()
export class EnviarDiscoService 
{
    public url;
    constructor(private _http: HttpClient) {
        this.url = environment.apiDifRap+"contacto";
     }

    enviar(disco:EnviarDisco)
    {
        let cabeceras = new HttpHeaders();
        cabeceras = cabeceras.set('Content-Type','application/json');
        return this._http.post(this.url+"/envianostudisco",disco,{headers: cabeceras});
    }
}