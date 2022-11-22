import { Component } from '@angular/core'
import { Router } from '@angular/router'
import { UserService } from 'src/app/Services/user.service'

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
})
export class ToolbarComponent {
  titleFlag = true
  isLoggedIn$: any
  UserName$: any

  constructor(private user: UserService, private router: Router) {}


  
  ngOnInit(): void {
    this.isLoggedIn$ = this.user.loggedInStatus
    this.UserName$ = this.user.currentUserName
  }
  logout() {
    this.user.logout()
    this.ngOnInit()
    this.router.navigate(['accounts/login'])
  }
}
