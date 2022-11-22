import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchAqiComponent } from './search-aqi.component';

describe('SearchAqiComponent', () => {
  let component: SearchAqiComponent;
  let fixture: ComponentFixture<SearchAqiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchAqiComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchAqiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
