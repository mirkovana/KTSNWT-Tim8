import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferTableItemComponent } from './offer-table-item.component';

describe('OfferTableItemComponent', () => {
  let component: OfferTableItemComponent;
  let fixture: ComponentFixture<OfferTableItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferTableItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferTableItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
