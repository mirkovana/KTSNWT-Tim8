import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainOffersViewComponent } from './main-offers-view.component';

describe('MainOffersViewComponent', () => {
  let component: MainOffersViewComponent;
  let fixture: ComponentFixture<MainOffersViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainOffersViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainOffersViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
