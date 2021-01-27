import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { hasUncaughtExceptionCaptureCallback } from 'process';

import { RatingService } from './rating.service';

describe('RatingService', () => {
  let service: RatingService;
  let httpMock: HttpTestingController;
  let http: HttpClient;

 


  beforeEach(() => {

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
       providers:    [RatingService]
    });

    TestBed.configureTestingModule({});
    service = TestBed.inject(RatingService);
    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  //afterEach(() => {
  //  httpMock.verify();
  //});

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return rating', fakeAsync(() => {
    let rating: any;

    let mockRating = {"id": 5, "rating": 5};

    service.bear = "1";
    service.getUsersRating(1).subscribe(data => {
      rating = data;
    })

    const req = httpMock.match("http://localhost:8080/api/ratings/rated/")[0];
    expect(req.request.method).toBe("GET");
    req.flush(mockRating);

    tick();


  }));
});
