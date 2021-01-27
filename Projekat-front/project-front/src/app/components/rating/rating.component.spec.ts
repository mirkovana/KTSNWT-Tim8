import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
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
    spyOn(component, 'openSnackBar');
  });

  //it('should create', () => {
  //  expect(component).toBeTruthy();
  //});
  /*it('should set up rated', fakeAsync(() => {
    spyOn(component, 'setUpRating');
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    tick();
    expect(component.setUpRating).toHaveBeenCalled();
    
    expect(component).toBeTruthy();
    expect(component.oldRating).toBe(3);
    expect(component.ratingId).toBe(5);
    expect(component.rated).toBe(true);
    expect(component.starRating).toBe(3);
    expect(component.updating).toBe(false);
    
  }))*/
  it('should set up rated', () => {
   
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    spyOn(component, 'setUpRating');
    //fixture.detectChanges();
    //expect(component.setUpRating).toHaveBeenCalled();
    expect(component).toBeTruthy();
    expect(component.oldRating).toBe(3);
    expect(component.ratingId).toBe(5);
    expect(component.rated).toBe(true);
    expect(component.starRating).toBe(3);
    expect(component.updating).toBe(false);
    
  })

  it ('should rate offer', fakeAsync(() => {
    
    spyOn(component, 'setUpRating');
    ratingService.getUsersRating();
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    expect(component.setUpRating).toHaveBeenCalled();
    console.log(component.oldRating + " \n\n\n\n\n\n")
    //component.starRating = 2;
    expect(component.oldRating).toBe(0);
    component.starRating = 5;   // setting new rating
    component.rateOffer();
    expect(ratingService.createRating).toHaveBeenCalled();
    tick();
    expect(component.rated).toBe(true);
    expect(component.ratingId).toBe(10);
    expect(component.openSnackBar).toHaveBeenCalledWith("Rating created.");

  }))

  it ('should delete rating', fakeAsync(() => {
    spyOn(component, 'setUpRating');
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    expect(component.setUpRating).toHaveBeenCalled();
    component.deleteRating();
    expect(ratingService.deleteRating).toHaveBeenCalled();
    tick();
    expect(component.rated).toBe(false);
    expect(component.starRating).toBe(0);
    expect(component.openSnackBar).toHaveBeenCalledWith("Rating deleted.");

  }))

  it ('should update rating', fakeAsync(() => {
    spyOn(component, 'setUpRating');
    component.loggedIn = "user@gmail.com";
    fixture.detectChanges();
    component.offerId = 1;
    fixture.detectChanges();
    expect(component.setUpRating).toHaveBeenCalled();
    component.starRating = 5;
    component.saveUpdate();
    expect(ratingService.updateRating).toHaveBeenCalled();
    tick();
    expect(component.updating).toBe(false);
    expect(component.openSnackBar).toHaveBeenCalledWith("Rating updated.");

  }))

});
