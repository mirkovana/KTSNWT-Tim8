import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../models/User';
import { UserTokenState } from '../models/UserTokenState';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
   }

   public get currentUserValue(): User {
    return this.currentUserSubject.value;
}

login(username: string, password: string) {
    return this.http.post<any>('http://localhost:8080/auth/log-in', { 'username':username,'password': password })
        .pipe(map(userTokenState => {
            // login successful if there's a jwt token in the response
            if (userTokenState.accessToken) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('token', userTokenState.accessToken);
                localStorage.setItem('username', username);
                //const jwt: JwtHelperService = new JwtHelperService();
                //console.log("jwt prosao");
                //const info = jwt.decodeToken(userTokenState.accessToken);
                //console.log("INFOOOOO: " + info);
                //let prom = JSON.stringify(info);
                //console.log(prom);
                //const role = info.role[0].authority;
                //console.log("ROLAAAA: " + role);
                //localStorage.setItem('role', info.role[0].authority);
                //this.currentUserSubject.next(user);
            }

            return userTokenState.accessToken;
        }));
}

logout() {
  // remove user from local storage to log user out
  localStorage.removeItem('token');
  //this.currentUserSubject.next(null);
}

}
