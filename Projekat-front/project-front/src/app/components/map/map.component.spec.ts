import { ChangeDetectorRef } from '@angular/core';
import { ComponentFixture, fakeAsync, TestBed } from '@angular/core/testing';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { MapComponent } from './map.component';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;
  let changeDetector: any;

  beforeEach(async () => {
    let changeDetectorRef = {
      detectChanges: jasmine.createSpy('detectChanges')
    };

    await TestBed.configureTestingModule({
      declarations: [MapComponent],
      providers: [
        { provide: ChangeDetectorRef, useValue: changeDetectorRef }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    changeDetector = TestBed.inject(ChangeDetectorRef);
    component.offers = {
      content: [
        {
          "id": 1,
          "description": "some desc",
          "title": "Offer1",
          "avgRating": 4.3,
          "nmbOfRatings": 100,
          "lon": 40.0,
          "lat": 50.0,
          "place": "minsk"
        }],
      totalElements: 1,
      totalPages: 1
    };
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should pin markers on map (onChanges)', fakeAsync(() => {
    component.ngOnChanges();
    fixture.detectChanges();
    expect(component.map).not.toBe(undefined);
    expect(component.offers).not.toBe(undefined);
    expect(component.on).toEqual(true);
    // component.pinMarkers()
    // expect(component.pinMarkers).toHaveBeenCalledWith(component.map, component.offers);
  }));



  it('should pin markers', () => {
    component.ngOnChanges();
    fixture.detectChanges();
    component.pinMarkers(component.map, component.offers);
    expect(component.alloffers.length).toEqual(1);
    expect(component.alloffers[0].title).toEqual('Offer1');
    expect(component.alloffers[0].id).toEqual(1);
    expect(component.alloffers[0].lon).toEqual(40.0);
    expect(component.alloffers[0].lat).toEqual(50.0);
    expect(component.alloffers[0].place).toEqual('minsk');
    expect(component.alloffers[0].description).toEqual('some desc');
    expect(component.alloffers[0].nmbOfRatings).toEqual(100);
    expect(component.alloffers[0].avgRating).toEqual(4.3);
    expect(component.on).toEqual(true);
    expect(component.markers.length).toEqual(1);
  });


  it('should delete markers', () => {
    expect(component.markers.length).toEqual(0);
  });

  it('should call delete', () => {
    component.ngOnChanges();
    fixture.detectChanges();
    component.on = true;
    component.delete();
    expect(component.on).toEqual(false);
    expect(component.markers.length).toEqual(0);

  })

  it('should call dodaj', () => {
    component.ngOnChanges();
    fixture.detectChanges();
    component.on = false;
    component.dodaj();
    fixture.detectChanges();
    expect(component.on).toEqual(true);
    expect(component.markers.length).toEqual(1);
  })

  it('should call deleteMarkers', () => {
    component.deleteMarkers(component.map);
    expect(component.markers.length).toEqual(0);
  });

  it('should call initMap', () => {
    expect(component.map).not.toBe(undefined);
  });





});
