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
      status: [{ value: ReimbursementStatus.PENDING, disabled: true }, Validators.required]
    });
  }

  submit() {
    this.submitted = true;
    if (this.reimbursementsForm.invalid) return;

    //getRawValue - returns all form values including disabled, prevents status is not sent
    const request: CreateReimbursementRequest = this.reimbursementsForm.getRawValue();

    this.reimbursementService.createReimbursement(request).subscribe({
      next: (res) => {
        this.createdReimbursement = res;
        //restores PENDING status, default formReset sets it to null
        this.reimbursementsForm.reset({
          status: ReimbursementStatus.PENDING
        });
        this.submitted = false;
      },
      error: (err) => console.error('Error creating reimbursement', err)
    });
  }
}
