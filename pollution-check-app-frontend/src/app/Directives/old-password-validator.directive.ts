import { Directive, Input, OnInit } from '@angular/core'
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms'
import { User } from '../Models/user'
import { UserService } from '../Services/user.service'

@Directive({
  selector: '[appOldPasswordValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: OldPasswordValidatorDirective,
      multi: true,
    },
  ],
})
export class OldPasswordValidatorDirective implements OnInit {
  userDetails: User = new User('', '', '', new Date())
  constructor(private user: UserService) {}
  ngOnInit(): void {
    let email = localStorage.getItem('email')
    if (email == null) {
      email = 'nouser'
    }
    this.user.getUser(email).subscribe((user) => {
      this.userDetails = user
    })
  }

  validate(control: AbstractControl): ValidationErrors | null {
    if (!(this.userDetails.password === control.value)) {
      return {
        oldPasswordValid: true,
      }
    } else {
      return null
    }
  }
}
