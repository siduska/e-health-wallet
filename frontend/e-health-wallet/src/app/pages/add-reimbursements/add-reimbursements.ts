import { Component } from '@angular/core';
import {ReimbursementsForm} from "../../components/reimbursements-form/reimbursements-form";
import {ReimbursementsTable} from "../../components/reimbursements-table/reimbursements-table";

@Component({
  selector: 'app-add-reimbursements',
    imports: [
        ReimbursementsForm,
        ReimbursementsTable
    ],
  templateUrl: './add-reimbursements.html',
  styleUrl: './add-reimbursements.css'
})
export class AddReimbursementsComponent {

}
