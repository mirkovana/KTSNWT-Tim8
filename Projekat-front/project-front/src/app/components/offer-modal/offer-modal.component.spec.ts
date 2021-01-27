import { HttpClient } from '@angular/common/http';
import { Route } from '@angular/compiler/src/core';
import { ChangeDetectorRef, SimpleChange } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { OfferImageService } from 'src/app/services/offer-image.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { of } from 'rxjs';
import { OfferModalComponent } from './offer-modal.component';
import { Offer } from 'src/app/models/Offer';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('OfferModalComponent', () => {
  let component: OfferModalComponent;
  let fixture: ComponentFixture<OfferModalComponent>;
  let imageService: any;
  let dom: any;
  let subService: any;
  let changeDetector: any;
  let router: any;

  beforeEach(async () => {

    let imageServiceMock = {
      getOfferImages: jasmine.createSpy('getOfferImages')
        .and.returnValue(of(({
          content:
            [{ id: 1, description: 'nesto', imageBase64: '11111' },
            { id: 2, description: 'nesto123', imageBase64: '22222' }],
          pageSize: 10,
          pageNumber: 0
        }))),

    };

    let domMock = {
      bypassSecurityTrustResourceUrl: jasmine.createSpy('bypassSecurityTrustResourceUrl')
        // .and.returnValue('1231231312312313123')
    };

    let subServiceMock = {
      subscribe: jasmine.createSpy('subscribe').and.returnValue(of(''))
    };

    let ChangeDetectorRefMock = {
      detectChanges: jasmine.createSpy("detectChanges")
    };

    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };


    await TestBed.configureTestingModule({
      declarations: [OfferModalComponent],
      imports: [ RouterTestingModule , HttpClientTestingModule],
      providers: [
        { provide: OfferImageService, useValue: imageServiceMock },
        { provide: DomSanitizer, useValue: domMock },
        { provide: SubscriptionService, useValue: subServiceMock },
        { provide: ChangeDetectorRef, useValue: ChangeDetectorRefMock },
        { provide: Router, useValue: routerMock },
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferModalComponent);
    component = fixture.componentInstance;
    imageService = TestBed.inject(OfferImageService);
    dom = TestBed.inject(DomSanitizer);
    subService = TestBed.inject(SubscriptionService);
    changeDetector = TestBed.inject(ChangeDetectorRef);
    router = TestBed.inject(Router);
    component.nesto = true;
    component.informacije = {"id": 1, "description": "some desc", "title": "Offer1", "avgRating": 4.3,
    "nmbOfRatings": 100, "lon": 40.0, "lat": 50.0, "place": "minsk"};
    fixture.detectChanges();

  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should call ngOnChanges', () => {
    component.ngOnChanges(new SimpleChange(false, true, true));
    fixture.detectChanges();
    expect(component.nesto).toEqual(true);
    expect(component.displayModal).toEqual(true);
    expect(component.offerImages.content.length).toEqual(2);
    expect(component.offerImages.content[0].description).toEqual('nesto');
    expect(component.offerImages.content[1].description).toEqual('nesto123');
    expect(component.offerImages.content[0].id).toEqual(1);
    expect(component.offerImages.content[1].id).toEqual(2);
    expect(component.offerImages.content[0].imageBase64).toEqual("11111");
    expect(component.offerImages.content[1].imageBase64).toEqual("22222");
    expect(dom.bypassSecurityTrustResourceUrl).toHaveBeenCalled();
    
  });

  it('should subscribe to offer', fakeAsync(()=>{
    component.subscribe(component.informacije);
    expect(subService.subscribe).toHaveBeenCalled();
    tick();
    expect(component.uslov).toEqual(true);
    expect(component.subSuccess).toEqual("Successfully subscribed to offer");
    expect(component.toastColor).toEqual("green-snackbar");
  }));

  it('should call edit', ()=>{
    component.edit(component.informacije.id);
    expect(router.navigate).toHaveBeenCalledWith(['edit-offer/'], { queryParams: { offerID: component.informacije.id } });
  });


});
