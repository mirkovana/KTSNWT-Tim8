import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Offer } from '../models/Offer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http: HttpClient) { }

  getOfferById():Observable<Offer>{
    return this.http.get<Offer>("http://localhost:8080/api/offers/"+localStorage.getItem('offerId'));
  }
}
