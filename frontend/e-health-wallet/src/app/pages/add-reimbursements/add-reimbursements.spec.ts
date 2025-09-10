import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddReimbursements } from './add-reimbursements';

describe('AddReimbursements', () => {
  let component: AddReimbursements;
  let fixture: ComponentFixture<AddReimbursements>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddReimbursements]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddReimbursements);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
