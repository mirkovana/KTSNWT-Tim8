import { EventEmitter } from '@angular/core';
import { ComponentFixture, fakeAsync, inject, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Router, RouterLinkWithHref } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Offer } from 'src/app/models/Offer';
import { OfferInfoService } from 'src/app/services/offer-info.service';

import { OfferItemComponent } from './offer-item.component';

describe('OfferItemComponent', () => {
  let component: OfferItemComponent;
  let fixture: ComponentFixture<OfferItemComponent>;
  let offerService: any;

  beforeEach(async () => {

    let offerServiceMock = {
      offerChosenEvent: { next: jasmine.createSpy('next') }
    }

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [ OfferItemComponent ],
      providers: [{provide: OfferInfoService, useValue: offerServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferItemComponent);
    component = fixture.componentInstance;
    offerService = TestBed.inject(OfferInfoService);
    component.offer = {"id": 1, "description": "some desc", "title": "Offer1", "avgRating": 4.3,
    "nmbOfRatings": 100, "lon": 40.0, "lan": 50.0, "place": "minsk"};
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to page of offer',  () => {
    const debugElement = fixture.debugElement.query(By.css('a.offer-item-link'))   //directive(RouterLinkWithHref));
    expect(debugElement.properties['href'] === "/offers/1").toBe(true);
  });

  it ('should emit clicked on view event', () => {
    const debugElement = fixture.debugElement.query(By.css('a.view-on-map'))
    let button = fixture.debugElement.nativeElement.querySelector('a.view-on-map');
    button.click();
    fixture.whenStable().then(() => {
      expect(component.viewOnMap).toHaveBeenCalled();
      expect(offerService.offerChosenEvent.next).toHaveBeenCalled();
    });
  })

});
