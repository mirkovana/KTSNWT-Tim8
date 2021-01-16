import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferImagePage } from '../models/OfferImage';

@Injectable({
  providedIn: 'root'
})
export class OfferImageService {

  constructor(
    private http: HttpClient
    //private authService: AuthService
  ) {}


  getOfferImages(id:number): Observable<OfferImagePage> {
    return this.http.get<OfferImagePage>("http://localhost:8080/api/Offer-images/"+id+"/0/20")
  }
}
