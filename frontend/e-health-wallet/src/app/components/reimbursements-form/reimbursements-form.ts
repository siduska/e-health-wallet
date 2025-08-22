import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ReimbursementDto} from '../../models/ReimbursementDto';
import {ReimbursementsService} from '../../services/reimbursements';
import {CreateReimbursementRequest} from '../../models/CreateReimbursementRequest';
import {ReimbursementStatus} from '../../models/ReimbursementStatus';

@Component({
  selector: 'app-reimbursements-form',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './reimbursements-form.html',
  styleUrl: './reimbursements-form.css'
})
export class ReimbursementsForm {
  reimbursementsForm: FormGroup;
  submitted = false;
  createdReimbursement?: ReimbursementDto;
  statuses = Object.values(ReimbursementStatus);

  constructor(private fb: FormBuilder, private reimbursementService: ReimbursementsService) {
    this.reimbursementsForm = this.fb.group({
      patientName: ['', Validators.required],
      identificationNumber: ['', Validators.required],
      medicalProcedure: ['', Validators.required],
      cost: [0.00, [Validators.required, Validators.min(0.00)]],
      status: [ReimbursementStatus.PENDING, Validators.required]
    });
  }

  submit() {
    this.submitted = true;
    if (this.reimbursementsForm.invalid) return;

    const request: CreateReimbursementRequest = this.reimbursementsForm.value;

    this.reimbursementService.createReimbursement(request).subscribe({
      next: (res) => {
        this.createdReimbursement = res;
        this.reimbursementsForm.reset();
        this.submitted = false;
      },
      error: (err) => console.error('Error creating reimbursement', err)
    });
  }
}
