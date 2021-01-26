import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Offer } from '../models/Offer';
import { Observable } from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  bear  = localStorage.getItem("token");
    
  headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear, 'content-type': 'application/json'})

  constructor(private http: HttpClient, private _snackBar: MatSnackBar) { }

  getOfferById(id):Observable<Offer>{
    return this.http.get<Offer>("http://localhost:8080/api/offers/"+ id);
  }

  deleteOffer(offerId: number){ 
    return this.http.delete("http://localhost:8080/api/offers/" + offerId, {headers: this.headers}).subscribe(data=>{console.log(data);this.openSnackBarSD();window.location.replace("http://localhost:4200/home");});
  }

  addOffer(offer:Offer, idSubcategory:number)  {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(offer);
    console.log(body)
    //console.log("USAAAAAAAAAAAAAAAAAAAAAAAAAAAOOOO")
    return this.http.post<any>('http://localhost:8080/api/offers/'+idSubcategory, body, {headers: this.headers});
    // return this.http.post<any>('http://localhost:8080/api/offers/'+idSubcategory, body, {headers: this.headers}).subscribe(
    //   (val) => {
    //       console.log("POST call successful value returned in body", 
    //                   val);
    //   },
    //   response => {
    //       console.log("POST call in error", response);
    //       this.openSnackBarUS();
    //   },
    //   () => {
    //       console.log("The POST observable is now completed.");
    //       this.openSnackBarS();
    //       location.replace("http://localhost:4200/home");
    //   });
      
 }

 updateOffer(offer:any) {
  //const body=JSON.stringify(offer);
  return this.http.put<any>('http://localhost:8080/api/offers/'+offer.id, offer, {headers: this.headers}).subscribe(
    (val) => {
        console.log("PUT call successful value returned in body", 
                    val);
    },
    response => {
        console.log("PUT call in error", response);
        this.openSnackBarUS();
    },
    () => {
        console.log("The PUT observable is now completed.");
        this.openSnackBarSE();
    });

 }

 openSnackBarS() {
  this._snackBar.open("Successfully added.", "OK", {
    duration: 2000,
  });
}

openSnackBarSE() {
  this._snackBar.open("Successfully edited.", "OK", {
    duration: 2000,
  });
}

openSnackBarSD() {
  this._snackBar.open("Successfully deleted.", "OK", {
    duration: 2000,
  });
}

openSnackBarUS() {
  this._snackBar.open("Error occurs!", "OK", {
    duration: 2000,
  });
}
}
