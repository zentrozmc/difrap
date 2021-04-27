
import { HttpClient,HttpParams,HttpHeaders  } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { Entidad } from '../models/entidad';

export interface OptionsType 
{
    headers?: HttpHeaders | { [header: string]: string | string[] };
    observe?:any;
    params?: HttpParams | { [param: string]: string | string[] };
    reportProgress?: boolean;
    responseType?:any;
    withCredentials?: boolean;
}
export abstract class InterfazServicio<P extends Entidad>
{
    protected url:string;
    protected _http:HttpClient;
    constructor(h:HttpClient)
    {
        this._http =h;
        this.url="";
    }
   
    public requestOptions():OptionsType
    {
        let requestOptions:OptionsType = 
        {
            headers: new HttpHeaders()
            .append('Content-Type', 'application/json')
            .append('Authorization',this.getToken().toString()),
        };
        return requestOptions;
    }

    public requestOptionsSinContent():OptionsType
    {
        let requestOptions:OptionsType = 
        { 
            headers: new HttpHeaders()
            .append('Authorization',this.getToken().toString())
        };
        return requestOptions;
    }

    public requestOptionsFile():OptionsType
    {
        let requestOptions:OptionsType = 
        { 
            headers: new HttpHeaders()
            .append('Content-Type', 'application/json')
            .append('Access-Control-Allow-Origin','*')
            .append('Authorization',this.getToken().toString())
            
        };
        return requestOptions;
    }

    public getToken(): string 
    {
        var token = sessionStorage.getItem("token");
        return token? token:"";
    }

    listar(pagina:number,registrosxpagina:number, estructura:P):Observable<any>
    {
        let requestOptions = this.requestOptions();
        let search = new HttpParams();
        if(estructura)
        {
            var map = new Map(Object.entries(estructura));
            Object.keys(estructura).forEach(key => 
            {
                search.append(key,map.get(key));
            }); 
        }
        requestOptions.params = search;
        return this._http.get(this.url+"?pagina="+pagina+"&numreg="+registrosxpagina,requestOptions);
    }

    agregar(estructura:P):Observable<any>
    {
        return this._http.post(this.url+"/agregar",estructura,this.requestOptions());
    }

    obtener(estructura:P):Observable<any>
    {
        return this._http.get(this.url+"/"+estructura.idIncremental,this.requestOptions());
    }

    modificar(estructura:P):Observable<any>
    {
        return this._http.put(this.url+"/modificar/"+estructura.idIncremental,estructura,this.requestOptions());
    }

    eliminar(estructura:P):Observable<any>
    {
        return this._http.delete(this.url+"/eliminar/"+estructura.idIncremental,this.requestOptions());
    }

    
}