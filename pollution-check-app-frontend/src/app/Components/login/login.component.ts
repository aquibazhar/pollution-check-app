import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { UserService } from 'src/app/Services/user.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  })

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private _snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {}

  result: any
  userNameData: any
  userName: string = ''

  login() {
    console.log(this.loginForm.value)
    this.userService.doLogin(this.loginForm.value).subscribe(
      (response) => {
        this.result = response
        this.userService
          .getUser(this.loginForm.value.email)
          .subscribe((data) => {
            this.userNameData = data
            console.log(this.userNameData)
            this.userName = this.userNameData.name
            console.log(this.userName)
            this.userService.loginUser(
              this.result.token,
              this.loginForm.value.email,
              this.userName,
              this.loginForm.value.password,
            )
            this.openSnackBar(this.result.message, 'Ok')
            this.router.navigate(['search'])
          })
      },
      (error) => {
        this.openSnackBar(error.error.message, 'Ok')
      },
    )
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: ['blue-snackbar'],
    })
  }
}
