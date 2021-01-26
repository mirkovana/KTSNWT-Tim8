import { ChangeDetectorRef, Injectable } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, of } from 'rxjs';
import { OfferImage, OfferImagePage } from 'src/app/models/OfferImage';
import { OfferImageService } from 'src/app/services/offer-image.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClientModule } from '@angular/common/http';
import { EditOfferComponent } from './edit-offer.component';
import { MatDialogModule } from '@angular/material/dialog';

@Injectable()
export class ActivatedRouteStub {

  // ActivatedRoute.params is Observable
  private subject = new BehaviorSubject(this.testParams);
  params = this.subject.asObservable();

  // Test parameters
  private _testParams: {};
  get testParams() { return this._testParams; }
  set testParams(params: {}) {
    this._testParams = params;
    this.subject.next(params);
  }

  // ActivatedRoute.snapshot.params
  get snapshot() {
    return { params: this.testParams };
  }
}



describe('EditOfferComponent', () => {
  let component: EditOfferComponent;
  let fixture: ComponentFixture<EditOfferComponent>;
  let router: any;
  let formBuilder: any;
  let changeDetector: any;
  let activatedRoute: any;
  let domSanitizer: any;
  let offerImageService: any;

  beforeEach(async () => {
    let routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    let FormBuilderMock = {
      group: jasmine.createSpy('group')
    };

    let ChangeDetectorRefMock = {
      detectChanges: jasmine.createSpy("detectChanges")
    };

    // let ActivatedRouteMock: ActivatedRouteStub = new ActivatedRouteStub();
    // ActivatedRouteMock.testParams = {offerID: 1};
    let ActivatedRouteMock = {
      queryParams: jasmine.createSpy("queryParams").and.returnValue({ offerID: 1 })
    }

    let DomSanitizerMOck = {
      bypassSecurityTrustResourceUrl: jasmine.createSpy("bypassSecurityTrustResourceUrl")
    };

    // let slika = new OfferImage(1, 'nesto', '132123');
    let OfferImageServiceMock = {
      getOfferImages: jasmine.createSpy('getOfferImages')
        .and.returnValue(of(({
          content:
          [{ id: 1, description: 'nesto', imageBase64: '132123' },
          { id: 2, description: 'nesto', imageBase64: '132123' }],
          pageSize: 10,
          pageNumber: 0
        }))),

      updateDescription: jasmine.createSpy('updateDescription').and.returnValue(of({})),

      deleteImage: jasmine.createSpy('deleteImage').and.returnValue(of({}))
    };

    await TestBed.configureTestingModule({
      declarations: [EditOfferComponent],
      imports: [FormsModule, HttpClientTestingModule, MatDialogModule],
      providers: [
        { provide: Router, useValue: Router },
        { provide: FormBuilder, useValue: FormBuilderMock },
        { provide: ChangeDetectorRef, useValue: ChangeDetectorRefMock },
        {
          provide: ActivatedRoute, useValue: {
            queryParams: of({
              offerID: 1
            })
          }
        },
        { provide: DomSanitizer, useValue: DomSanitizerMOck },
        { provide: OfferImageService, useValue: OfferImageServiceMock },
      ]

    })
      .compileComponents();
    fixture = TestBed.createComponent(EditOfferComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    formBuilder = TestBed.inject(FormBuilder);
    changeDetector = TestBed.inject(ChangeDetectorRef);
    activatedRoute = TestBed.inject(ActivatedRoute);
    domSanitizer = TestBed.inject(DomSanitizer);
    offerImageService = TestBed.inject(OfferImageService);
  });

  // beforeEach(() => {

  // activatedRoute.testParams = { offerID: 1 };
  // fixture.detectChanges();
  // });

  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should get offerID and load offer images', fakeAsync(() => {
    component.ngOnInit();
    fixture.detectChanges();
    expect(offerImageService.getOfferImages).toHaveBeenCalledWith(1);
    // fixture.detectChanges();  // ngOnInit will be called
    tick();

    // expect(component.images.content.length).toEqual(1);
  }));
});
