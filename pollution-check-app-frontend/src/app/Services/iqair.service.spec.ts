import { TestBed } from '@angular/core/testing';

import { IqairService } from './iqair.service';

describe('IqairService', () => {
  let service: IqairService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IqairService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
