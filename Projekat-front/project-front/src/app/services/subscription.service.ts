import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { auth_token}  from '../models/app.constants'

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor( private http: HttpClient) { }
  // constants.au .auth_token = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImtvcjFAbmVzdG8uY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjEwODM2MDU4LCJleHAiOjE2MTA4Mzc4NTh9.BSCu6HCg9IM6qmjogCzjISnx8xxzZPrGTpVRBdA1mlSMc4x2koS9MHslkkoHbB4BITNU_ShGNqXKD_lLToTMoQ';

  unsubscribe(id: number) {
    this.http.delete<any>('http://localhost:8080/api/offers/unsubscribe/' + id, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) }
    ).subscribe(data => {
      console.log(data);
    }
    );
  }

  subscribe(id: number) {
    let header = new HttpHeaders().append('Authorization', `Bearer ${auth_token}`);
    // header.append({observe: 'response'});
    return this.http.post<any>('http://localhost:8080/api/offers/subscribe/' +  id, {}, { headers: header },
    )
  }


}
