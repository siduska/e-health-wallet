import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReimbursementsForm } from './reimbursements-form';

describe('ReimbursementsForm', () => {
  let component: ReimbursementsForm;
  let fixture: ComponentFixture<ReimbursementsForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReimbursementsForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReimbursementsForm);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
