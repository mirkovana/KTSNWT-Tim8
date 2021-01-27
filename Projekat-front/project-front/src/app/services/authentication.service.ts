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
            if (userTokenState.accessToken) {
                localStorage.setItem('token', userTokenState.accessToken);
                localStorage.setItem('username', username);
                console.log(userTokenState.accessToken)
            }
            else{
              console.log("nema tokena")
            }

            return userTokenState.accessToken;
        }));
}

logout() {
  // remove user from local storage to log user out
  localStorage.removeItem('token');
  localStorage.removeItem('username');
}

isLoggedIn(): boolean {
  if (!localStorage.getItem('username')) {
      return false;
  }
  return true;
}

}
