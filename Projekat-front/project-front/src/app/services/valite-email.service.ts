import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ValiteEmailService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor( private http: HttpClient, private _snackBar: MatSnackBar) { }





  validateEmail(token:string)  {
    //const headers = { 'content-type': 'application/json'}  
    
    
    return this.http.post<any>('http://localhost:8080/auth/validateEmail/' + token, {headers: this.headers}).subscribe(
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
          location.replace("http://localhost:4200/login");
      });
      
 }

 openSnackBarS() {
  this._snackBar.open("Your account is active.", "OK", {
    duration: 4000,
  });
}

openSnackBarUS() {
  this._snackBar.open("Error occurs!", "OK", {
    duration: 2000,
  });
}
}
