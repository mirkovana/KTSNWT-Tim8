import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class ValiteEmailService {
	private headers = new HttpHeaders({'Content-Type': 'application/json'});
  constructor( private http: HttpClient) { }





  validateEmail(token:string)  {
    const headers = { 'content-type': 'application/json'}  
    
    
    return this.http.post<any>('http://localhost:8080/auth/validateEmail/' + token, {headers: this.headers}).subscribe(
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
