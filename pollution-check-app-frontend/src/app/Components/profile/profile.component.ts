import { ThisReceiver } from '@angular/compiler'
import { Component, OnInit } from '@angular/core'
import { FormBuilder, Validators } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { User } from 'src/app/Models/user'
import { UserService } from 'src/app/Services/user.service'

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  changePasswordForm = this.fb.group({
    password: ['', [Validators.required]],
    newPassword: [
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

  passwordTyped: string = ''
  oldPasswordTyped: string = ''

  changePassword: boolean = false
  type: string = 'password'
  model = new User('', '', '', new Date())

  userDetails: User = new User('', '', '', new Date())

  constructor(
    private fb: FormBuilder,
    private user: UserService,
    private _snackBar: MatSnackBar,
  ) {}

  ngOnInit(): void {
    let email = localStorage.getItem('email')
    if (email == null) {
      email = 'nouser'
    }
    this.user.getUser(email).subscribe((user) => {
      this.userDetails = user
      this.model = this.userDetails
    })
  }

  onClick() {
    if (this.type === 'password') {
      this.type = 'text'
    } else {
      this.type = 'password'
    }
  }

  onChange(): void {
    this.passwordTyped = this.changePasswordForm.value.newPassword
  }
  onChangePassword() {
    this.userDetails.password = this.changePasswordForm.value.newPassword
    this.user.changeUserPassword(this.userDetails).subscribe((user) => {
      console.log(user)
      this.openSnackBar('Password successfully updated', 'Ok')
      this.changePasswordForm.value.password = ''
      this.changePasswordForm.value.newPassword = ''
      this.changePasswordForm.value.confirmPassword = ''
      this.changePassword = !this.changePassword
      this.changePasswordForm.reset(this.changePasswordForm.value)
      this.ngOnInit()
    })
  }

  onChangeOld() {
    this.oldPasswordTyped = this.changePasswordForm.value.password
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
      panelClass: ['blue-snackbar'],
    })
  }
}
