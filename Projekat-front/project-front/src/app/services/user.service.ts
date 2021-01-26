import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import {User} from '../models/User'
import { Observable } from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
@Injectable({
  providedIn: 'root'
})
export class UserService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient, private _snackBar: MatSnackBar

  ) { }

  addUser(user:User)  {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(user);
    return this.http.post<any>('http://localhost:8080/auth/sign-up',body, {headers: this.headers}).subscribe(
      (val) => {
          console.log("POST call successful value returned in body", 
                      val);
      },
      response => {
          console.log("POST call in error", response);
          this.openSnackBarUS();
      },
      () => {
          console.log("The POST observable is now completed.");
          this.openSnackBarS();
      });
      
 }

 openSnackBarS() {
  this._snackBar.open("Check your email.", "OK", {
    duration: 4000,
  });
}

openSnackBarUS() {
  this._snackBar.open("Error occurs!", "OK", {
    duration: 2000,
  });
}
}
  