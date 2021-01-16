import { TestBed } from '@angular/core/testing';

import { OfferImageService } from './offer-image.service';

describe('OfferImageService', () => {
  let service: OfferImageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferImageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
