import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from "rxjs/operators" 
import { Offer, Page } from '../models/Offer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferInfoService {

  constructor(
    private http: HttpClient
    //private authService: AuthService
  ) {}


  getOffers() {
    return this.http.get<Page>("http://localhost:8080/api/offers")//.pipe(map(res => {
    //  return res;
    //}));
  }

  
}
