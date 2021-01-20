import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class RatingService{
    
    url: string = "http://localhost:8080/api/ratings"

    bear = localStorage.getItem("token");

    headers = new HttpHeaders({"Authorization": "Bearer " + this.bear})
        
    
    constructor(private http: HttpClient){}


    setupHeader(){

        this.bear = localStorage.getItem("token");
        this.headers = new HttpHeaders({"Authorization": "Bearer " + this.bear})
        
    }

    getUsersRating(offerId: number){

        let headerss = new HttpHeaders({"Authorization": "Bearer " + localStorage.getItem('token')})

        return this.http.get(this.url + "/rated/" + offerId, {headers: headerss});
    }

    updateRating(ratingId: number, rating: number){
        this.setupHeader();
        return this.http.put(this.url + "/" + ratingId, {"rating": rating}, {headers: this.headers})
    }

    createRating(offerId: number, rating: number) {
        this.setupHeader();
        return this.http.post(this.url + "/" + offerId, {"rating": rating}, {headers: this.headers});
    }

    deleteRating(ratingId: number){
        this.setupHeader();
        return this.http.delete(this.url + "/" + ratingId, {headers: this.headers});
    }

}