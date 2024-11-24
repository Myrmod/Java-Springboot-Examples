import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";

import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: "./app.component.html",
  styleUrl: "./app.component.scss"
})
export class AppComponent {
  title = "angular_spring_boot_frontend";
}
