import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/usuario';
import { environment } from 'src/environments/environment';
import { InterfazServicio } from './interfaz-servicio';
import { Observable } from 'rxjs';

@Injectable()
export class UsuarioService extends InterfazServicio<Usuario>
{
    constructor(protected _http: HttpClient)
    {
        super(_http);
        this.url=environment.api+"usuario";
    }

   
    consola(estructura:Usuario):Observable<any>
    {
        return this._http.post(this.url+"/consola",estructura,this.requestOptions());
    }

    login(estructura:Usuario):Observable<any>
    {
        let requestOptions = this.requestOptions();
        requestOptions.observe="response";
        return this._http.put(environment.api+"login",estructura,requestOptions);
    }

    refresca_token():Observable<any>
    {
        return this._http.get(this.url+"/refrescar_token",this.requestOptions());
    }
    
    obtener(estructura:Usuario):Observable<any>
    {
        return this._http.get(this.url+"/"+estructura.usuario,this.requestOptions());
    }

    actualizarCuenta(usuario:Usuario):Observable<any>
    {
        return this._http.put(this.url+"/actualizarCuenta",usuario,this.requestOptions());
    }

    recuperarPassword(usuario:Usuario):Observable<any>
    {
        return this._http.put(this.url+"/recuperarPassword",usuario,this.requestOptions());
    }

    actualizarPassword(usuario:Usuario):Observable<any>
    {
        return this._http.put(this.url+"/actualizarPassword",usuario,this.requestOptions());
    }

}