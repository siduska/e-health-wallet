import { Routes } from '@angular/router';
import { AddReimbursementsComponent } from './pages/add-reimbursements/add-reimbursements';
import { ReviewReimbursementsComponent } from './pages/review-reimbursements/review-reimbursements';
import {LoginComponent} from './pages/login/login';
import {AuthGuard} from './services/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/review', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'review', component: ReviewReimbursementsComponent, canActivate: [AuthGuard] },
  { path: 'add', component: AddReimbursementsComponent, canActivate: [AuthGuard] }
];
