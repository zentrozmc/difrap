import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerSteamIdComponent } from './ver-steam-id.component';

describe('VerSteamIdComponent', () => {
  let component: VerSteamIdComponent;
  let fixture: ComponentFixture<VerSteamIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerSteamIdComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerSteamIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
