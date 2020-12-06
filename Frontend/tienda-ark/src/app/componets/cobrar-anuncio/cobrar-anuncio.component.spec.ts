import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CobrarAnuncioComponent } from './cobrar-anuncio.component';

describe('CobrarAnuncioComponent', () => {
  let component: CobrarAnuncioComponent;
  let fixture: ComponentFixture<CobrarAnuncioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CobrarAnuncioComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CobrarAnuncioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
