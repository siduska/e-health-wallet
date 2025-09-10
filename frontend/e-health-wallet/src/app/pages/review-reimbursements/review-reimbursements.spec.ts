import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewReimbursements } from './review-reimbursements';

describe('ReviewReimbursements', () => {
  let component: ReviewReimbursements;
  let fixture: ComponentFixture<ReviewReimbursements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReviewReimbursements]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReviewReimbursements);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
