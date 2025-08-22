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
  reimbursements: Iterable<ReimbursementDto> = [];

  constructor(private reimbursementService: ReimbursementsService) {}

  ngOnInit() {
    this.reimbursementService.reimbursements$.subscribe({
      next: (res) => this.reimbursements = res
    });

    this.reimbursementService.loadReimbursements();
  }

  protected readonly of = of;
}
