export interface CreateReimbursementRequest {
  patientName: string;
  identificationNumber: string;
  medicalProcedure: string;
  cost: number;
  status: string;
}
