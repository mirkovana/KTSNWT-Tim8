import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
// import { auth_token}  from '../models/app.constants'

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  auth_token = localStorage.getItem('token');

  constructor( private http: HttpClient) { }
  // constants.au .auth_token = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImtvcjFAbmVzdG8uY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjEwODM2MDU4LCJleHAiOjE2MTA4Mzc4NTh9.BSCu6HCg9IM6qmjogCzjISnx8xxzZPrGTpVRBdA1mlSMc4x2koS9MHslkkoHbB4BITNU_ShGNqXKD_lLToTMoQ';

  unsubscribe(id: number) {
    return this.http.delete<any>('http://localhost:8080/api/offers/unsubscribe/' + id, { headers: new HttpHeaders().append('Authorization', `Bearer ${localStorage.getItem('token')}`) }
    )
  }

  subscribe(id: number) {
    console.log(this.auth_token);
    // this.auth_token = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImtvcjFAbmVzdG8uY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjEwOTc1MTcyLCJleHAiOjE2MTA5NzY5NzJ9.HSACBc-lVOW3ntk06iZyvl95M9nV1BdtONvZS-ZYBI7h7nblxOKz8OdOVxxHhWWNtzPDrCI8-KEnmSkc935kWg'
    let header = new HttpHeaders().append('Authorization', `Bearer ${localStorage.getItem('token')}`);
    // header.append({observe: 'response'});
    return this.http.post<any>('http://localhost:8080/api/offers/subscribe/' +  id, {}, { headers: header },
    )
  }


}
