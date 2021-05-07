import {Injectable, Inject} from '@angular/core';
import {HttpClient,  } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { InterfazServicio } from './interfaz-servicio';
import { Dino } from '../models/dino';
import { Observable } from 'rxjs';

@Injectable()
export class DinoService extends InterfazServicio<Dino>
{
    constructor(protected _http: HttpClient)
    {
        super(_http);
        this.url=environment.api+"dino";
    }
    
    comprar(estructura:Dino):Observable<any>
    {
        return this._http.put(this.url+"/"+estructura.idIncremental+"/comprar",estructura,this.requestOptions());
    }
}