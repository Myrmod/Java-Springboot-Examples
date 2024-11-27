import { ActivatedRoute, Router } from "@angular/router";
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule
} from "@angular/forms";

import { Component } from "@angular/core";
import { Employee } from "../../Model/employee";
import { EmployeeService } from "../../Service/employee.service";

@Component({
  selector: "app-employee-update",
  imports: [ReactiveFormsModule, FormsModule],
  templateUrl: "./employee-update.component.html",
  styleUrl: "./employee-update.component.scss"
})
export class EmployeeUpdateComponent {
  employee: Employee = new Employee();

  // controls
  employeeUpdateForm = new FormGroup({
    firstName: new FormControl(""),
    lastName: new FormControl(""),
    emailID: new FormControl("")
  });

  constructor(
    private employeeService: EmployeeService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.employeeService
      .getEmployee(Number(this.route.snapshot.params["id"]))
      .subscribe(data => {
        if (!data.emailID || !data.firstName || !data.lastName) {
          console.warn(`Received faulty employee data: `, data);
        }
        this.employee = data;

        // fill form
        this.employeeUpdateForm.setValue({
          firstName: this.employee.firstName,
          lastName: this.employee.lastName,
          emailID: this.employee.emailID
        });
      });
  }

  onSubmit() {
    this.updateEmployee();
  }

  updateEmployee() {
    if (
      !(
        this.employeeUpdateForm.value.firstName &&
        this.employeeUpdateForm.value.lastName &&
        this.employeeUpdateForm.value.emailID &&
        this.employee.id
      )
    ) {
      return;
    }

    this.employee.firstName = this.employeeUpdateForm.value.firstName;
    this.employee.lastName = this.employeeUpdateForm.value.lastName;
    this.employee.emailID = this.employeeUpdateForm.value.emailID;

    this.employeeService.updateEmployee(this.employee).subscribe({
      complete: () => {
        this.goToEmployeeList();
      },
      error: console.error
    });
  }

  goToEmployeeList() {
    this.router.navigate(["/employees"]);
  }
}
