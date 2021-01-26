import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddOfferImageComponent } from './add-offer-image.component';

describe('AddOfferImageComponent', () => {
  let component: AddOfferImageComponent;
  let fixture: ComponentFixture<AddOfferImageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddOfferImageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddOfferImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
