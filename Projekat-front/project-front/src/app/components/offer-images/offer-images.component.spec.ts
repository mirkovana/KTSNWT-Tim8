import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferImagesComponent } from './offer-images.component';

describe('OfferImagesComponent', () => {
  let component: OfferImagesComponent;
  let fixture: ComponentFixture<OfferImagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfferImagesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferImagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
