import { TestBed } from '@angular/core/testing';

import { BasicAuthHttpInterceptor } from './basic-auth-http.interceptor';

describe('BasicAuthHttpInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      BasicAuthHttpInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: BasicAuthHttpInterceptor = TestBed.inject(BasicAuthHttpInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
