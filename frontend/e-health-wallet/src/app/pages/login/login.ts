import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth-service';
import { LoginRequest } from '../../models/LoginRequest';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  submitted = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit(): void {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    const request: LoginRequest = this.loginForm.value;


    this.authService.login(request).subscribe({
      next: user => {
        console.log("Login successful", user); // <-- should see this
        this.router.navigate(['/review']);
      },
      error: err => {
        console.error("Login failed", err);
        this.errorMessage = 'Invalid username or password';
      }
    });
  }
}
