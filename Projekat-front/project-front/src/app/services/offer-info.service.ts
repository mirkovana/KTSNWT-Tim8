import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { map } from "rxjs/operators"

@Injectable({
  providedIn: 'root'
})
export class OfferInfoService {

  constructor(private http: HttpClient ) { }

  getOffers() {
    return this.http.get("http://localhost:8080/api/offers").pipe(map(res => {
      return res;
    }));
  }
}
