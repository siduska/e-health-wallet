export interface ReimbursementDto {
  id: number;
  patientName: string;
  identificationNumber: string;
  medicalProcedure: string;
  cost: number;
  status: string;
  description?: string; // optional field
}
