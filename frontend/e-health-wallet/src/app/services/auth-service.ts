import {BehaviorSubject, Observable, tap} from 'rxjs';
import {User} from '../models/User';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {LoginRequest} from '../models/LoginRequest';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = environment.apiUrl;
  private apiUrl = this.baseUrl + '/users/login';
  private username = 'admin';
  private password = 'password';
  private basicAuth = 'Basic ' + btoa(`${this.username}:${this.password}`);

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    const saved = localStorage.getItem('user');
    if (saved) {
      this.currentUserSubject.next(JSON.parse(saved));
    }
  }

  login(request: LoginRequest): Observable<User> {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    return this.http.post<User>(this.apiUrl, request, { headers }).pipe(
      tap(user => {
        console.log("authorized");
        this.currentUserSubject.next(user);
        localStorage.setItem('user', JSON.stringify(user));
      })
    );
  }

  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('user');
  }

  getUser(): User | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return !!this.currentUserSubject.value;
  }
}
