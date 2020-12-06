import {Injectable, Inject} from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import { map } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import { Anuncio } from '../models/anuncio';
import { environment } from 'src/environments/environment';

@Injectable()
export class AnuncioService
{

    public url;
    constructor(private _http: Http)
    {
        this.url=environment.api+"anuncio";
    }

    public requestOptions():RequestOptions
    {
        var token = sessionStorage.getItem("token");
        let requestOptions:RequestOptions  = new RequestOptions(
        { 
            headers: new Headers
            ({
                'Content-Type': 'application/json',
                'Authorization': token ? token : ""
            })
        });
        return requestOptions;
    }

    agregar(estructura:Anuncio)
    {
        return this._http.post(this.url+"/agregar",estructura,this.requestOptions()).map(res=>res.json());
    }
    obtener(id:any)
    {
        return this._http.get(this.url+"/"+id,this.requestOptions()).map(res=>res.json());
    }

    listar()
    {
        return this._http.get(this.url+"",this.requestOptions()).map(res=>res.json());
    }
    activar(id:any)
    {
        return this._http.put(this.url+"/activar/"+id,null,this.requestOptions()).map(res=>res.json());
    }
    cobrar(id:any)
    {
        return this._http.put(this.url+"/cobrarPuntos/"+id,null,this.requestOptions()).map(res=>res.json());
    }
}