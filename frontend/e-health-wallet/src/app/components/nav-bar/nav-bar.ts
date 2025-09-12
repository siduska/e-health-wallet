import { Component } from '@angular/core';
import {User} from '../../models/User';
import {AuthService} from '../../services/auth-service';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  imports: [
    RouterLinkActive,
    RouterLink
  ],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.css'
})
export class NavbarComponent {
  user: User | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.authService.currentUser$.subscribe(u => (this.user = u));
  }

  async logout() {
    this.authService.logout();
    await this.router.navigate(['/login']);
  }
}
