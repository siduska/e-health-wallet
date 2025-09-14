import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {ReimbursementDto} from '../models/ReimbursementDto';
import {ReimbursementWithLogDto} from '../models/ReimbursementWithLogDto';
import {CreateReimbursementRequest} from '../models/CreateReimbursementRequest';
import { EMPTY } from 'rxjs';
import {Page} from '../models/Page';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReimbursementsService {

  private apiUrl = environment.apiUrl;

  private baseUrl = this.apiUrl + '/reimbursements';

  private username = 'admin';
  private password = 'password';
  private basicAuth = 'Basic ' + btoa(`${this.username}:${this.password}`);

  constructor(private http: HttpClient) {}

  private reimbursementsSubject = new BehaviorSubject<ReimbursementDto[]>([]);
  reimbursements$ = this.reimbursementsSubject.asObservable();
  private reimbursementsPageSubject = new BehaviorSubject<Page<ReimbursementWithLogDto> | null>(null);
  reimbursementsPage$ = this.reimbursementsPageSubject.asObservable();

  loadReimbursements(): void {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    this.http.get<ReimbursementDto[]>(this.baseUrl, { headers })
      .subscribe({
        next: (res) => this.reimbursementsSubject.next(res),
        error: (err) => console.error('Error loading reimbursements', err)
      });
  }

  loadPendingReimbursements(): void {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    this.http.get<ReimbursementDto[]>(
      this.baseUrl + "/filter",
      { headers,
        params: { filter: 'PENDING' }
      })
      .subscribe({
        next: (res) => this.reimbursementsSubject.next(res),
        error: (err) => console.error('Error loading reimbursements', err)
      });
  }

  loadReimbursementsWithLog(page: number = 0, size: number = 10): void {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });

    this.http.get<Page<ReimbursementWithLogDto>>(this.baseUrl + '/all', {
      headers,
      params: { page: page.toString(), size: size.toString() }
    }).pipe(
      tap((res) => this.reimbursementsPageSubject.next(res))
    ).subscribe({
      error: (err) => console.error('Error loading reimbursements', err)
    });
  }

  createReimbursement(request: CreateReimbursementRequest): Observable<ReimbursementDto> {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    return this.http.post<ReimbursementDto>(this.baseUrl, request, { headers }).pipe(
      tap(() => this.loadPendingReimbursements()) // to update table after creation
    );
  }

  deleteReimbursement(id: number): Observable<void> {
    if (!confirm('Are you sure you want to delete this reimbursement?')) {
      return EMPTY;
    }
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    return this.http.delete<void>(`${this.baseUrl}/${id}`, { headers }).pipe(
      tap(() => this.loadPendingReimbursements()) // refresh table
    );
  }

}
