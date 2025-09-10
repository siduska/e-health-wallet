export interface ReimbursementWithLogDto {
  id: number;
  patientName: string;
  identificationNumber: string;
  medicalProcedure: string;
  cost: number;
  status: string;
  description?: string;
}
