import { ChangeDetectorRef } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { OfferService } from 'src/app/services/offer.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { of } from 'rxjs';
import { DisplayOffer, SubscriptionTableComponent } from './subscription-table.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatPaginator } from '@angular/material/paginator';

describe('SubscriptionTableComponent', () => {
  let component: SubscriptionTableComponent;
  let fixture: ComponentFixture<SubscriptionTableComponent>;
  let offerService: any;
  let cd: any;
  let subService: any;

  beforeEach(async () => {

    let offerServiceMock = {
      getSubscriptions: jasmine.createSpy('getSubscriptions')
        .and.returnValue(of({
          content:
            [{
              "id": 1, "description": "some desc", "title": "Offer1", "avgRating": 4.3,
              "nmbOfRatings": 100, "lon": 40.0, "lat": 50.0, "place": "minsk"
            },
            {
              "id": 2, "description": "some desc1", "title": "Offer2", "avgRating": 4.3,
              "nmbOfRatings": 101, "lon": 40.0, "lat": 50.0, "place": "minsk"
            }],
          totalElements: 2,
          totalPages: 1
        }))
    };

    let subscriptionServiceMock = {
      unsubscribe: jasmine.createSpy('unsubscribe').and.returnValue(of())
    };

    let changeDetectorMock = {
      detectChanges: jasmine.createSpy('detectChanges')
    };

    await TestBed.configureTestingModule({
      declarations: [SubscriptionTableComponent, MatPaginator],
      imports: [HttpClientTestingModule],
      providers: [
        { provide: OfferService, useValue: offerServiceMock },
        { provide: SubscriptionService, useValue: subscriptionServiceMock },
        { provide: ChangeDetectorRef, useValue: changeDetectorMock }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubscriptionTableComponent);
    component = fixture.componentInstance;
    offerService = TestBed.get(OfferService);
    subService = TestBed.inject(SubscriptionService);
    cd = TestBed.inject(ChangeDetectorRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call ngAfterViewInit', fakeAsync(() => {
    component.ngAfterViewInit();
    expect(component.paginator.pageSize = 1);
    expect(component.paginator.pageIndex = 0);
    let sub = spyOn(offerService.getSubscriptions(), 'subscribe');
    tick();
    expect(offerService.getSubscriptions).toHaveBeenCalled();
  }));



  it('should call usub', fakeAsync(() => {
    component.unsub(new DisplayOffer(1, 'opis', 1, 'naslov'));
    expect(subService.unsubscribe).toHaveBeenCalledWith(2);
    tick();
    expect(component.subSuccess).toBe("Successfully unsubscribed from offer");
  }));
});
