import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from "rxjs/operators"
import { Offer, Page } from '../models/Offer';
import { Observable } from 'rxjs';
// import { Rating } from '../models/Rating';
import { EventEmitter } from 'events';
import { PageEvent } from '@angular/material/paginator';
import { auth_token}  from '../models/app.constants'
import { FilterParameters } from '../models/Filter';
import { PaginatorPageable } from '../models/PaginatorPageable';

@Injectable({
  providedIn: 'root'
})
export class OfferInfoService {
 

  constructor(
    private http: HttpClient) {}


  getOffers() {
    return this.http.get<Page>("http://localhost:8080/api/offers");
  }

  getSubscriptions(page, size){
    return this.http.get<Page>("http://localhost:8080/api/users/getSubscriptions/"+page+"/"+size, { headers: new HttpHeaders().append('Authorization', `Bearer ${auth_token}`) });
  }

  getOffersPage(pageable: PaginatorPageable){
    console.log("get offers page + " + pageable.pageSize + " " + pageable.pageIndex);
    return this.http.get<Page>("http://localhost:8080/api/offers?size=" + pageable.pageSize
     + "&page=" + pageable.pageIndex);
}


filterOffers2(filter: FilterParameters, pageable: PaginatorPageable){
    let subcatStr = "";
    if (filter.subcats){
        if (filter.subcats.length > 0){
            subcatStr = "&subcategories="
            for (let id of filter.subcats){
                //subcatStr +="&subcategories=" + id;
            }
            for (let ia of filter.subcats){
                subcatStr += ia + ",";    
            }
            subcatStr = subcatStr.substring(0, subcatStr.length-1)
        }
    }
    return this.http.get<Page>("http://localhost:8080/api/offers/filter?name=" + filter.name
    + "&place=" + filter.place + subcatStr + "&page=" + pageable.pageIndex + "&size=" + pageable.pageSize);
}


  
}
