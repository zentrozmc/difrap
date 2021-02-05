import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './componets/login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { CommonModule } from '@angular/common';
import { MenuComponent } from './componets/menu/menu.component';
import { AnunciosComponent } from './componets/anuncios/anuncios.component';
import { ItemsComponent } from './componets/items/items.component';
import { VerSteamIdComponent } from './componets/ver-steam-id/ver-steam-id.component';
import { MenuService } from './services/menu.service';
import { CobrarAnuncioComponent } from './componets/cobrar-anuncio/cobrar-anuncio.component';
import { CuentaComponent } from './componets/cuenta/cuenta.component';
import { NgxSpinnerModule } from "ngx-spinner";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MenuComponent,
    AnunciosComponent,
    ItemsComponent,
    VerSteamIdComponent,
    CobrarAnuncioComponent,
    CuentaComponent
    
  ],
  imports: [
    NgxSpinnerModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    CommonModule
  ],
  providers: [MenuService],
  bootstrap: [AppComponent]
})
export class AppModule { }
