import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class RatingService{
    
    url: string = "http://localhost:8080/api/ratings"

    // ovo ne radi... radi samo kad ovde rucno pasteujem accesstoken iz postmana
    bear = localStorage.getItem("token");

    headers = new HttpHeaders({"Authorization": "Bearer " + this.bear})

    constructor(private http: HttpClient){}

    getUsersRating(offerId: number){
        return this.http.get(this.url + "/rated/" + offerId, {headers: this.headers});
    }

    updateRating(ratingId: number, rating: number){
        return this.http.put(this.url + "/" + ratingId, {"rating": rating}, {headers: this.headers})
    }

    createRating(offerId: number, rating: number) {
        return this.http.post(this.url + "/" + offerId, {"rating": rating}, {headers: this.headers});
    }

    deleteRating(ratingId: number){
        return this.http.delete(this.url + "/" + ratingId, {headers: this.headers});
    }

}