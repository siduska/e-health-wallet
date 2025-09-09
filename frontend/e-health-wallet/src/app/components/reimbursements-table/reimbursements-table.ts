import {Component, OnInit} from '@angular/core';
import {ReimbursementDto} from '../../models/ReimbursementDto';
import {ReimbursementsService} from '../../services/reimbursements';
import {of} from 'rxjs';

@Component({
  selector: 'app-reimbursements-table',
  templateUrl: './reimbursements-table.html',
  styleUrl: './reimbursements-table.css'
})
export class ReimbursementsTable implements OnInit {
  reimbursements: ReimbursementDto[] = [];

  constructor(private reimbursementService: ReimbursementsService) {}

  ngOnInit() {
    this.reimbursementService.reimbursements$.subscribe({
      next: (res) => this.reimbursements = res
    });

    this.reimbursementService.loadPendingReimbursements();
  }

  deleteReimbursement(id: number): void {
    this.reimbursementService.deleteReimbursement(id).subscribe({
      next: () => {
        this.reimbursements = this.reimbursements.filter(r => r.id !== id);
      },
      error: (err) => console.error('Error deleting reimbursement', err)
    });
  }

  protected readonly of = of;
}
