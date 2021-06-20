import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AlbumesComponent } from './components/albumes/albumes.component';
import { VerAlbumComponent } from './components/ver-album/ver-album.component';
import { EnviarDiscoComponent } from './components/enviar-disco/enviar-disco.component';
import { HomeComponent } from './components/home/home.component';


const routes: Routes = [ 
  {path: '', component: HomeComponent},
  {path: 'albumes', component: AlbumesComponent,},
  {path:'albumes/:id', component: VerAlbumComponent},
  {path: 'enviardisco', component: EnviarDiscoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
