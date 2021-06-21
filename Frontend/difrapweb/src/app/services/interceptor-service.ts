import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import {Injectable} from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Injectable()
export class InterceptorService implements HttpInterceptor
{

    public contador=0;
    constructor(
        private _spinner: NgxSpinnerService
    )
    {
    }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.abrirSpiner();
        return next.handle(req).pipe(
            finalize(() => this.cerrarSpiner())
        );
    }

    private abrirSpiner(){
        this._spinner.show();
        this.contador++;
    }
    private cerrarSpiner()
    {
        if(this.contador==0)
            this._spinner.hide();
        this.contador--;
        if(this.contador==0)
            this._spinner.hide();
    }
}