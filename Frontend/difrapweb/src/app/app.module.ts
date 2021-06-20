import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AlbumesComponent } from './components/albumes/albumes.component';
import { VerAlbumComponent } from './components/ver-album/ver-album.component';
import { EnviarDiscoComponent } from './components/enviar-disco/enviar-disco.component';
import { ReproductorComponent } from './components/reproductor/reproductor.component';
import { PaginadorComponent } from './components/util/paginador/paginador.component';
import { HomeComponent } from './components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    AlbumesComponent,
    VerAlbumComponent,
    EnviarDiscoComponent,
    ReproductorComponent,
    PaginadorComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
