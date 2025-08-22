import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReimbursementsTable } from './reimbursements-table';

describe('ReimbursementsTable', () => {
  let component: ReimbursementsTable;
  let fixture: ComponentFixture<ReimbursementsTable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReimbursementsTable]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReimbursementsTable);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
