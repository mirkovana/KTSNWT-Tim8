import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from "rxjs/operators"
import { Offer, Page } from '../models/Offer';
import { Observable } from 'rxjs';
import { Rating } from '../models/Rating';
import { EventEmitter } from 'events';
import { PageEvent } from '@angular/material/paginator';
import { auth_token}  from '../models/app.constants'

@Injectable({
  providedIn: 'root'
})
export class OfferInfoService {
 

  constructor(
    private http: HttpClient
  ) { }

  // auth_token: String = 'eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzcHJpbmctc2VjdXJpdHktZXhhbXBsZSIsInN1YiI6ImtvcjFAbmVzdG8uY29tIiwiYXVkIjoid2ViIiwiaWF0IjoxNjEwODMxNjk1LCJleHAiOjE2MTA4MzM0OTV9.-MbwL_VRUdsxLIRMMXMDsmyzbc6-WU9HwDupnLQzk-cSXzp5-glnXvQNEDREBlnF4i25RXOyZINZVupc_Re1og';


  getOffers() {
    return this.http.get<Page>("http://localhost:8080/api/offers");
  }

  getSubscriptions(page, size){
    return this.http.get<Page>("http://localhost:8080/api/users/getSubscriptions/"+page+"/"+size, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) });
  }

  sendRating(id: number, rating: number) {
    this.http.post<any>('http://localhost:8080/api/ratings/' + id, { rating: rating }, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) }
    ).subscribe(data => {
      console.log(data);
    }
    );
  }

  getUsersRating(id:number):Observable<Rating> {
    return this.http.get<Rating>("http://localhost:8080/api/ratings/getRating/" + id, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) });
  }

  updateRating(id: number, rating: number) {
    this.http.put<any>('http://localhost:8080/api/ratings/' + id, { rating: rating }, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) }
    ).subscribe(data => {
      console.log(data);
    }
    );
  }


}
