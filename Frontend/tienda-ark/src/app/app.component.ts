import { Component, OnInit, ViewChild } from '@angular/core';
import { MenuComponent } from './componets/menu/menu.component';
import { MenuService } from './services/menu.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit
{
  @ViewChild(MenuComponent) public menu:any;
  title = 'tienda-ark';
  constructor(
    private _menuService:MenuService
  )
  {
   
  }
  ngOnInit() 
  {
   
  }

  ngAfterViewChecked(){
    this._menuService.setMenu(this.menu);
  }


}
