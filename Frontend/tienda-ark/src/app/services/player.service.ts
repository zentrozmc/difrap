import {Injectable, Inject} from '@angular/core';
import { Headers, Http, RequestOptions } from '@angular/http';
import { map } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import { environment } from 'src/environments/environment';

@Injectable()
export class PlayerService
{

    public url;
    constructor(private _http: Http)
    {
        this.url=environment.api+"player";
    }

    listar()
    {
        return this._http.get(this.url).map(res=>res.json());
    }

}