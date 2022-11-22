import { Directive, Input } from '@angular/core'
import {
  AbstractControl,
  NG_VALIDATORS,
  ValidationErrors,
  Validator,
} from '@angular/forms'

@Directive({
  selector: '[appOldConfirmValidator]',
  providers: [
    {
      provide: NG_VALIDATORS,
      useExisting: OldConfirmValidatorDirective,
      multi: true,
    },
  ],
})
export class OldConfirmValidatorDirective {
  constructor() {}
  @Input() oldPassword: any
  validate(control: AbstractControl): ValidationErrors | null {
    if (this.oldPassword === control.value) {
      return {
        newPasswordValid: true,
      }
    } else {
      return null
    }
  }
}
