import {Component, OnInit} from '@angular/core';
import {ReimbursementsService} from '../../services/reimbursements';
import {ReimbursementWithLogDto} from '../../models/ReimbursementWithLogDto';
import {Page} from '../../models/Page';

@Component({
  selector: 'app-review-reimbursements',
  imports: [],
  templateUrl: './review-reimbursements.html',
  styleUrl: './review-reimbursements.css'
})
export class ReviewReimbursementsComponent implements OnInit {
  page: Page<ReimbursementWithLogDto> | null = null;
  reimbursementsWithLog: ReimbursementWithLogDto[] = [];

  constructor(private reimbursementService: ReimbursementsService) {}

  ngOnInit() {
    this.reimbursementService.reimbursementsPage$.subscribe({
      next: (res) => {
        if (!res) return;
        this.page = res;
        this.reimbursementsWithLog = res.content;
      }
    });

    this.loadPage(0);
  }

  loadPage(pageNumber: number) {
    this.reimbursementService.loadReimbursementsWithLog(pageNumber, 10);
  }
}
