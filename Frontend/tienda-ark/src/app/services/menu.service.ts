import {Injectable} from '@angular/core';
import { MenuComponent } from '../componets/menu/menu.component';
import { UsuarioService } from './usuario.service';
@Injectable()
export class MenuService 
{
    public menu:MenuComponent;
    constructor(
    )
    {
        let nulo:any;
        this.menu = new MenuComponent(nulo,nulo,nulo);
    }

    setMenu(m:MenuComponent) 
    {
        this.menu = m;
    }

    getMenu() 
    {
        return this.menu;
    }
}