import { Routes } from '@angular/router';
import { AddReimbursementsComponent } from './pages/add-reimbursements/add-reimbursements';
import { ReviewReimbursementsComponent } from './pages/review-reimbursements/review-reimbursements';

export const routes: Routes = [
  { path: '', redirectTo: '/review', pathMatch: 'full' },
  { path: 'review', component: ReviewReimbursementsComponent },
  { path: 'add', component: AddReimbursementsComponent }
];
