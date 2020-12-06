import {Injectable, Inject} from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import { map } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import { Usuario } from '../models/usuario';
import { environment } from 'src/environments/environment';

@Injectable()
export class UsuarioService
{

    public url;
    constructor(private _http: Http)
    {
        this.url=environment.api+"usuario";
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
    login(estructura:Usuario)
    {
        return this._http.put(environment.api+"login",estructura);
    }

    refresca_token()
    {
        return this._http.get(this.url+"/refrescar_token",this.requestOptions()).map(res=>res.json());
    }
    agregar(estructura:Usuario)
    {
        return this._http.post(this.url+"/agregar",estructura,this.requestOptions()).map(res=>res.json());
    }

    obtener(usuario:any)
    {
        return this._http.get(this.url+"/"+usuario,this.requestOptions()).map(res=>res.json());
    }

    actualizarCuenta(usuario:any)
    {
        return this._http.put(this.url+"/actualizarCuenta",usuario,this.requestOptions()).map(res=>res.json());
    }

    actualizarPassword(usuario:any)
    {
        return this._http.put(this.url+"/actualizarPassword",usuario,this.requestOptions()).map(res=>res.json());
    }

}