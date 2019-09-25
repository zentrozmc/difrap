import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VerAlbumComponent } from './ver-album.component';

describe('VerAlbumComponent', () => {
  let component: VerAlbumComponent;
  let fixture: ComponentFixture<VerAlbumComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VerAlbumComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VerAlbumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
