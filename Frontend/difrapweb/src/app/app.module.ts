import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlbumesComponent } from './components/albumes/albumes.component';
import { VerAlbumComponent } from './components/ver-album/ver-album.component';
import { EnviarDiscoComponent } from './components/enviar-disco/enviar-disco.component';
import { ReproductorComponent } from './components/reproductor/reproductor.component';
import { PaginadorComponent } from './components/util/paginador/paginador.component';
import { HomeComponent } from './components/home/home.component';
import { InterceptorService } from './services/interceptor-service';
import { NgxSpinnerModule } from 'ngx-spinner';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    AlbumesComponent,
    VerAlbumComponent,
    EnviarDiscoComponent,
    ReproductorComponent,
    PaginadorComponent,
    HomeComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxSpinnerModule,
    BrowserAnimationsModule
  ],
  providers: [
    {provide:HTTP_INTERCEPTORS, useClass: InterceptorService,multi:true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
