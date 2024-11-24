import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule
} from "@angular/forms";

import { Component } from "@angular/core";
import { Employee } from "../../Model/employee";
import { EmployeeService } from "../../Service/employee.service";
import { Router } from "@angular/router";

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

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  onSubmit() {
    console.log(this.employeeCreationForm.value);
    this.createEmployee();
  }

  createEmployee() {
    this.employeeService.createEmployee(this.employee).subscribe({
      complete: console.log,
      error: console.error
    });
  }

  goToEmployeeList() {
    this.router.navigate(["/employees"]);
  }
}
