import {Component, OnInit} from '@angular/core';
import {ReimbursementDto} from '../../models/ReimbursementDto';
import {ReimbursementsService} from '../../services/reimbursements';
import {of} from 'rxjs';
import {ReimbursementNotificationService} from '../../services/ReimbursementNotificationService';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-reimbursements-table',
  templateUrl: './reimbursements-table.html',
  styleUrl: './reimbursements-table.css'
})
export class ReimbursementsTable implements OnInit {
  reimbursements: ReimbursementDto[] = [];

  constructor(
    private reimbursementService: ReimbursementsService,
    private reimbursementNotificationService: ReimbursementNotificationService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.reimbursementService.reimbursements$.subscribe({
      next: (res) => this.reimbursements = res
    });

    this.reimbursementService.loadPendingReimbursements();

    this.reimbursementNotificationService.watchClaimUpdates()
      .subscribe(event => {
        this.toastr.info(`Reimbursement #${event.identificationNumber} changed status to: ${event.newStatus}`, 'Info', {
          positionClass: 'toast-top-right'
        });
      });
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
