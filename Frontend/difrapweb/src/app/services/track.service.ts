
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams, } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Track } from '../models/track';
import { Observable } from "rxjs";

@Injectable()
export class TrackService 
{
    public url;
    constructor(private _http: HttpClient) {
        this.url = environment.apiDifRap+"track";
     }

    listar(pagina:number,album:Track):Observable<any>
    {
        let cabeceras = new HttpHeaders();
        cabeceras = cabeceras.set('Content-Type','application/json');
        let params = new HttpParams();
        params = params.append("pagina",pagina.toString());
        params = params.append("numreg",environment.regXpag.toString());
        for(let key in album)
        {
            if(album[key]!=null)
                params = params.append(key, album[key]);
        }
        return this._http.get(this.url,{headers: cabeceras,params});
    }

    obtener(album:Track)
    {
        let cabeceras = new HttpHeaders();
        cabeceras = cabeceras.set('Content-Type','application/json');
        return this._http.get(this.url+"/"+album.idIncremental,{headers: cabeceras});
    }
}