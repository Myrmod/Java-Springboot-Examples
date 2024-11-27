import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Employee } from "../Model/employee";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class EmployeeService {
  private baseUrl = "http://localhost:8080/api/v1";

  constructor(private httpClient: HttpClient) {}

  httpOptions = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  };

  getEmployees(): Observable<Array<Employee>> {
    return this.httpClient.get<Array<Employee>>(
      `${this.baseUrl}/employees`,
      this.httpOptions
    );
  }

  getEmployee(id: number): Observable<Employee> {
    return this.httpClient.get<Employee>(
      `${this.baseUrl}/employees/${id}`,
      this.httpOptions
    );
  }

  createEmployee(employee: Employee): Observable<Object> {
    return this.httpClient.post(`${this.baseUrl}/employees`, employee);
  }

  updateEmployee(employee: Employee): Observable<Object> {
    console.log("updating employee");
    return this.httpClient.put(
      `${this.baseUrl}/employees/${employee.id}`,
      employee
    );
  }
}
