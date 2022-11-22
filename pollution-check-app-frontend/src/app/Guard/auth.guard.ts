import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../Services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private user:UserService,private router:Router){}
  result:any;
  valid!:boolean;
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.user.getToken()==null){
        this.router.navigate(['accounts/login']);
        console.log("token null")
        return false;
      }
    if(this.user.isLoggedIn()){
      return true;
    }
    else{
      this.router.navigate(['accounts/login']);
      return false;
    }
  }
  
}
