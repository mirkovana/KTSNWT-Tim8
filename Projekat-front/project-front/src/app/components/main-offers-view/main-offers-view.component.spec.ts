import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { OfferService } from 'src/app/services/offer.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MainOffersViewComponent } from './main-offers-view.component';
import { of } from 'rxjs';

describe('MainOffersViewComponent', () => {
  let component: MainOffersViewComponent;
  let fixture: ComponentFixture<MainOffersViewComponent>;
  let offerService: any;

  beforeEach(async () => {

    let offerServiceMock = { getOffersPage: jasmine.createSpy('getOffersPage').and.returnValues(of({"content":[{}], 
  'nesto': 10, "nesto2": 5}), of("")),
    filterOffers2: jasmine.createSpy('filterOffers2').and.returnValues(of(""), of(""))}

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ MainOffersViewComponent ],
      providers: [{provide: OfferService, useValue: offerServiceMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainOffersViewComponent);
    component = fixture.componentInstance;
    offerService = TestBed.inject(OfferService);
   
  });

  it('should create', fakeAsync(() => {
    component.ngOnInit();
    fixture.detectChanges();
    tick();
    //expect(offerService.getOffersPage).toHaveBeenCalled();
    expect(component.offersPage.content.length).toBe(1);
    expect(component).toBeTruthy();
  }));
});
