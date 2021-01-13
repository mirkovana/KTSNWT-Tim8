import { TestBed } from '@angular/core/testing';

import { OfferInfoService } from './offer-info.service';

describe('OfferInfoService', () => {
  let service: OfferInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OfferInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
