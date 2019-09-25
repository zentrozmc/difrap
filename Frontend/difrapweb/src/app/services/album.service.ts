
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders, HttpParams, } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Album } from '../models/album';

@Injectable()
export class AlbumService 
{
    public url;
    constructor(private _http: HttpClient) {
        this.url = environment.apiDifRap+"albumes";
     }

    listar(pagina:number,album:Album)
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

    obtener(album:Album)
    {
        let cabeceras = new HttpHeaders();
        cabeceras = cabeceras.set('Content-Type','application/json');
        return this._http.get(this.url+"/"+album.idIncremental,{headers: cabeceras});
    }
}