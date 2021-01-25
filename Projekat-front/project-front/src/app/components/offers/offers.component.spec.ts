import { DebugElement, EventEmitter } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Page } from 'src/app/models/Offer';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { OfferInfoService } from 'src/app/services/offer-info.service';

import { OffersComponent } from './offers.component';

describe('OffersComponent', () => {
  let component: OffersComponent;
  let fixture: ComponentFixture<OffersComponent>;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      declarations: [ OffersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OffersComponent);
    component = fixture.componentInstance;
    component.pageChanged = new EventEmitter<PaginatorPageable>();
    spyOn(component.pageChanged, 'emit');
    component.paginatorDetails = new PaginatorPageable(0, 5, 0, 10);
    component.page = new Page([{"id": 1, "description": "some desc", "title": "Offer1", "avgRating": 4.3,
  "nmbOfRatings": 100, "lon": 40.0, "lan": 50.0, "place": "minsk"}], 15, 3)
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create and display offers', () => {
    let elements: DebugElement[] = fixture.debugElement.queryAll(By.css('app-offer-item'));
    expect(elements.length).toBe(1);
  });

  it('should emit page changed event', () => {
    let event = new PaginatorPageable(0, 1, 5, 0);
    component.onClick(event);
    expect(component.pageChanged.emit).toHaveBeenCalledWith(event);
    
  });

});
