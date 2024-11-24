import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule
} from "@angular/forms";

import { Component } from "@angular/core";
import { Employee } from "../../Model/employee";

@Component({
  selector: "app-employee-create",
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: "./employee-create.component.html",
  styleUrl: "./employee-create.component.scss"
})
export class EmployeeCreateComponent {
  employee: Employee = new Employee();

  // controls
  employeeCreationForm = new FormGroup({
    firstName: new FormControl(""),
    lastName: new FormControl(""),
    emailID: new FormControl("")
  });

  onSubmit() {
    console.log(this.employeeCreationForm.value);
  }
}
