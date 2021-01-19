import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subcategory } from '../models/Subcategory';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {

  constructor(private http: HttpClient) { }

  getAllSubcategories():Observable<Subcategory[]>{
    return this.http.get<Subcategory[]>("http://localhost:8080/api/subcategories");
  }
}
