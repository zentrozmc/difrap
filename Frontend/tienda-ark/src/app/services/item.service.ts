import {Injectable, Inject} from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import { map } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import { Item } from '../models/item';
import { environment } from 'src/environments/environment';

@Injectable()
export class ItemService
{

    public url;
    constructor(private _http: Http)
    {
        this.url=environment.api+"item";
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

    agregar(estructura:Item)
    {
        return this._http.post(this.url+"/agregar",estructura,this.requestOptions()).map(res=>res.json());
    }
    obtener(id:any)
    {
        return this._http.get(this.url+"/"+id,this.requestOptions()).map(res=>res.json());
    }

    listar(tipo:any)
    {
        return this._http.get(this.url+"?tipo="+tipo,this.requestOptions()).map(res=>res.json());
    }

    comprar(id:any)
    {
        return this._http.put(this.url+"/"+id+"/comprar",null,this.requestOptions()).map(res=>res.json());
    }

    gacha()
    {
        return this._http.get(this.url+"/gacha",this.requestOptions()).map(res=>res.json());
    }
    
}