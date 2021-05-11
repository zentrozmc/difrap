import {Injectable, Inject} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Item } from '../models/item';
import { environment } from 'src/environments/environment';
import { InterfazServicio } from './interfaz-servicio';
import { Observable } from 'rxjs';

@Injectable()
export class ItemService extends InterfazServicio<Item>
{
    constructor(protected _http: HttpClient)
    {
        super(_http);
        this.url=environment.api+"item";
    }

    comprar(id:any):Observable<any>
    {
        return this._http.put(this.url+"/"+id+"/comprar",null,this.requestOptions());
    }

    gacha():Observable<any>
    {
        return this._http.get(this.url+"/gacha",this.requestOptions());
    }   
}