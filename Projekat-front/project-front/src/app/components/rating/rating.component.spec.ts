import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RatingService } from 'src/app/services/rating.service';

import { RatingComponent } from './rating.component';

import { of } from 'rxjs';

describe('RatingComponent', () => {
  let component: RatingComponent;
  let fixture: ComponentFixture<RatingComponent>;
  let ratingService: any;
  let _snackBar: any;

  beforeEach(async () => {
    

    let ratingServiceMock = {
      getUsersRating: jasmine.createSpy("getUsersRating").and.returnValues(of({"rating": 3, "id": 5}), of(null)),
      createRating: jasmine.createSpy("createRating").and.returnValue(of({"rating": 5, "id": 10})),
      updateRating: jasmine.createSpy("updateRating").and.returnValue(of({"rating": 4, "id": 10})),
      deleteRating: jasmine.createSpy("deleteRating").and.returnValue(of(''))
    }

    let snackBarMock = {
      open: jasmine.createSpy("open")
    }

    await TestBed.configureTestingModule({
      declarations: [ RatingComponent ],
      providers: [{provide: RatingService, useValue: ratingServiceMock},
        {provide: MatSnackBar, useValue: snackBarMock}]
  
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingComponent);
    component = fixture.componentInstance;
    ratingService = TestBed.inject(RatingService);
    _snackBar = TestBed.inject(MatSnackBar);
    //fixture.detectChanges();
  });

  //it('should create', () => {
  //  expect(component).toBeTruthy();
  //});
  it('should set up rated', () => {
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    expect(component).toBeTruthy();
    expect(component.oldRating).toBe(3);
    expect(component.ratingId).toBe(5);
    expect(component.rated).toBe(true);
    expect(component.starRating).toBe(3);
    expect(component.updating).toBe(false);
    
  })

  it ('should rate offer', () => {
    ratingService.getUsersRating();
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    console.log(component.oldRating + " \n\n\n\n\n\n")
    //component.starRating = 2;
    expect(component.oldRating).toBe(0);
    // rate
  })

});
