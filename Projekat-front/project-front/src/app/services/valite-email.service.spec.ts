import { TestBed } from '@angular/core/testing';

import { ValiteEmailService } from './valite-email.service';

describe('ValiteEmailService', () => {
  let service: ValiteEmailService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValiteEmailService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
