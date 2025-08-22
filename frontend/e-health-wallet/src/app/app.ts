import {Component, OnInit, signal} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ReimbursementsForm } from './components/reimbursements-form/reimbursements-form';
import { ReimbursementsTable } from './components/reimbursements-table/reimbursements-table';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ReimbursementsForm, ReimbursementsTable],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {

  protected readonly title = signal('e-health-wallet');

  ngOnInit(): void {
  }
}
