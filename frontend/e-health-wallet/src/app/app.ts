import {Component, OnInit, signal} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NavbarComponent} from './components/nav-bar/nav-bar';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App implements OnInit {

  protected readonly title = signal('e-health-wallet');

  ngOnInit(): void {
  }
}
