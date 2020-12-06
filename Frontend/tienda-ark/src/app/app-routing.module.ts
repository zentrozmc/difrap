import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AnunciosComponent } from './componets/anuncios/anuncios.component';
import { CobrarAnuncioComponent } from './componets/cobrar-anuncio/cobrar-anuncio.component';
import { CuentaComponent } from './componets/cuenta/cuenta.component';
import { ItemsComponent } from './componets/items/items.component';
import { LoginComponent } from './componets/login/login.component';
import { VerSteamIdComponent } from './componets/ver-steam-id/ver-steam-id.component';

const routes: Routes = 
[
  {path: '', component: LoginComponent  },
  {path: 'verAnuncio', component: AnunciosComponent},
  {path: 'cobrarAnuncio/:id', component: CobrarAnuncioComponent},
  {path: 'comprarItems', component: ItemsComponent},
  {path: 'verSteamId', component: VerSteamIdComponent},
  {path: 'cuenta', component: CuentaComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
