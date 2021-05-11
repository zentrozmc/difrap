import {Injectable, Inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Anuncio } from '../models/anuncio';
import { environment } from 'src/environments/environment';
import { InterfazServicio } from './interfaz-servicio';
import { Observable } from 'rxjs';

@Injectable()
export class AnuncioService extends InterfazServicio<Anuncio>
{

    public url;
    constructor(protected _http: HttpClient)
    {
        super(_http)
        this.url=environment.api+"anuncio";
    }

    activar(id:any):Observable<any>
    {
        return this._http.put(this.url+"/activar/"+id,null,this.requestOptions());
    }
    cobrar(id:any):Observable<any>
    {
        return this._http.put(this.url+"/cobrarPuntos/"+id,null,this.requestOptions());
    }
}