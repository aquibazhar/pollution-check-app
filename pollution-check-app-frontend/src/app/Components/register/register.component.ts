import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { UserService } from 'src/app/Services/user.service'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private router: Router,
    private _snackBar: MatSnackBar,
    private userService: UserService,
  ) {}
  passwordTyped: string = ''
  registerForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(6)]],
    email: ['', [Validators.required, Validators.email]],
    password: [
      '',
      [
        Validators.required,
        Validators.pattern(
          /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/,
        ),
      ],
    ],
    confirmPassword: ['', [Validators.required]],
  })
  result: any
  ngOnInit(): void {}

  register() {
    console.log(this.registerForm.value)
    this.userService.register(this.registerForm.value).subscribe(
      (response) => {
        this.result = response
        console.log(this.result)
        this.openSnackBar('Registered Successfully', 'Ok')
        this.router.navigate(['accounts/login'])
      },
      (error) => {
        this.openSnackBar(String(error).substring(7), 'Ok')
      },
    )
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 3000,
      panelClass: ['blue-snackbar'],
    })
  }

  onChange(): void {
    this.passwordTyped = this.registerForm.value.password
  }
}
