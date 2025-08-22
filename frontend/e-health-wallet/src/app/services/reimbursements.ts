import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, tap} from 'rxjs';
import {ReimbursementDto} from '../models/ReimbursementDto';
import {CreateReimbursementRequest} from '../models/CreateReimbursementRequest';

@Injectable({
  providedIn: 'root'
})
export class ReimbursementsService {

  private baseUrl = 'http://localhost:8080/reimbursements';

  private username = 'admin';
  private password = 'password';
  private basicAuth = 'Basic ' + btoa(`${this.username}:${this.password}`);

  constructor(private http: HttpClient) {}

  private reimbursementsSubject = new BehaviorSubject<ReimbursementDto[]>([]);
  reimbursements$ = this.reimbursementsSubject.asObservable();

  getAllReimbursements(): Observable<Iterable<ReimbursementDto>> {
    const headers = new HttpHeaders({
      'Authorization': this.basicAuth
    });
    return this.http.get<ReimbursementDto[]>(this.baseUrl, { headers });
  }

  loadReimbursements(): void {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    this.http.get<ReimbursementDto[]>(this.baseUrl, { headers })
      .subscribe({
        next: (res) => this.reimbursementsSubject.next(res),
        error: (err) => console.error('Error loading reimbursements', err)
      });
  }

  createReimbursement(request: CreateReimbursementRequest): Observable<ReimbursementDto> {
    const headers = new HttpHeaders({ 'Authorization': this.basicAuth });
    return this.http.post<ReimbursementDto>(this.baseUrl, request, { headers }).pipe(
      tap(() => this.loadReimbursements()) // to update table after creation
    );
  }

}
