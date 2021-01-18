import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSubscriptionsViewComponent } from './main-subscriptions-view.component';

describe('MainSubscriptionsViewComponent', () => {
  let component: MainSubscriptionsViewComponent;
  let fixture: ComponentFixture<MainSubscriptionsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainSubscriptionsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainSubscriptionsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
