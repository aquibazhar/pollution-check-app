import { Directive, Input } from '@angular/core'
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms'

@Directive({
  selector: '[appConfirmPasswordValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: ConfirmPasswordValidatorDirective,
      multi: true,
    },
  ],
})
export class ConfirmPasswordValidatorDirective implements Validator {
  constructor() {}

  @Input() password: any
  validate(control: AbstractControl): ValidationErrors | null {
    if (!(this.password === control.value)) {
      return {
        confirmPasswordValid: true,
      }
    } else {
      return null
    }
  }
}
