import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnviarDiscoComponent } from './enviar-disco.component';

describe('EnviarDiscoComponent', () => {
  let component: EnviarDiscoComponent;
  let fixture: ComponentFixture<EnviarDiscoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnviarDiscoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnviarDiscoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
