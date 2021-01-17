import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import {User} from '../models/User'
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UserService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor(
    private http: HttpClient

  ) { }

  addUser(user:User)  {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(user);
    console.log(body)
    console.log("USAAAAAAAAAAAAAAAAAAAAAAAAAAAOOOO")
    return this.http.post<any>('http://localhost:8080/auth/sign-up',body, {headers: this.headers}).subscribe(
      (val) => {
          console.log("POST call successful value returned in body", 
                      val);
      },
      response => {
          console.log("POST call in error", response);
      },
      () => {
          console.log("The POST observable is now completed.");
      });
      
 }
}
  