import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login';
import { AuthGuard } from './services/auth-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/review', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'review',
    loadComponent: () =>
      import('./pages/review-reimbursements/review-reimbursements')
        .then(m => m.ReviewReimbursementsComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'add',
    loadComponent: () =>
      import('./pages/add-reimbursements/add-reimbursements')
        .then(m => m.AddReimbursementsComponent),
    canActivate: [AuthGuard]
  }
];

