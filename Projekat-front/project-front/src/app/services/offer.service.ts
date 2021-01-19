import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Offer } from '../models/Offer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  bear  = localStorage.getItem("token");
    
  headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear, 'content-type': 'application/json'})

  constructor(private http: HttpClient) { }

  getOfferById():Observable<Offer>{
    return this.http.get<Offer>("http://localhost:8080/api/offers/"+localStorage.getItem('offerId'));
  }

  deleteOffer(offerId: number){
    console.log("USAOOO U BRISANJE OFFERAAAA");
    console.log("ID OFFERA JEEEE: "+offerId);
    return this.http.delete("http://localhost:8080/api/offers/" + offerId, {headers: this.headers}).subscribe(data=>{console.log(data);});
  }

  addOffer(offer:Offer, idSubcategory:number)  {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(offer);
    console.log(body)
    console.log("USAAAAAAAAAAAAAAAAAAAAAAAAAAAOOOO")
    return this.http.post<any>('http://localhost:8080/api/offers/'+idSubcategory, body, {headers: this.headers}).subscribe(
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
