import {Component, OnInit, signal} from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import { ReimbursementsForm } from './components/reimbursements-form/reimbursements-form';
import { ReimbursementsTable } from './components/reimbursements-table/reimbursements-table';
import { AddReimbursementsComponent } from './pages/add-reimbursements/add-reimbursements';
import { ReviewReimbursementsComponent } from './pages/review-reimbursements/review-reimbursements';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {

  protected readonly title = signal('e-health-wallet');

  ngOnInit(): void {
  }
}
