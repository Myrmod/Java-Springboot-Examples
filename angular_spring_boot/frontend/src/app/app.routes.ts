import { EmployeeCreateComponent } from "./Routes/employee-create/employee-create.component";
import { EmployeeListComponent } from "./Routes/employee-list/employee-list.component";
import { EmployeeUpdateComponent } from "./Routes/employee-update/employee-update.component";
import { HomeComponent } from "./Routes/home/home.component";
import { Routes } from "@angular/router";

export const routes: Routes = [
  {
    path: "",
    component: HomeComponent
  },
  {
    path: "employees",
    component: EmployeeListComponent
  },
  {
    path: "employees/create",
    component: EmployeeCreateComponent
  },
  {
    path: "employees/update/:id",
    component: EmployeeUpdateComponent
  }
];
