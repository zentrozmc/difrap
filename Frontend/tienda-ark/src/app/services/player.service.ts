import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable()
export class PlayerService
{

    public url;
    constructor(private _http: HttpClient)
    {
        this.url=environment.api+"player";
    }

    listar():Observable<any>
    {
        return this._http.get(this.url);
    }

}