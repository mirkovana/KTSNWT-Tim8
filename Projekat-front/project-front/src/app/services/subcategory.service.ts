import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subcategory } from '../models/Subcategory';
import { Observable } from 'rxjs';
import { Category } from '../models/Category';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  bear  = localStorage.getItem("token");
    
  headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear, 'content-type': 'application/json'})
  constructor(private http: HttpClient, private _snackBar: MatSnackBar) { }

  getAllSubcategories():Observable<Subcategory[]>{
    return this.http.get<Subcategory[]>("http://localhost:8080/api/subcategories");
  }
  getAllCategories():Observable<Category[]>{
    return this.http.get<Category[]>("http://localhost:8080/api/categories");
  }

  addNewCategory(category:Category )  {
    const headers = { 'content-type': 'application/json'}  
    const body=JSON.stringify(category);
     
    return this.http.post<any>('http://localhost:8080/api/categories', body, {headers: this.headers}).subscribe(
      (val) => {
          console.log("POST call successful value returned in body", 
                      val);
      },
      response => {
          console.log("POST call in error", response);
          this.openSnackBarUS();
      },
      () => {
          console.log("The POST observable is now completed.");
          this.openSnackBarS();
          location.reload();
      });
      
 }

 addNewSubcategory(subcategory:Subcategory, idCat:number )  {
  const headers = { 'content-type': 'application/json'}  
  const body=JSON.stringify(subcategory);
   
  return this.http.post<any>('http://localhost:8080/api/subcategories/' + idCat, body, {headers: this.headers}).subscribe(
    (val) => {
        console.log("POST call successful value returned in body", 
                    val);
    },
    response => {
        console.log("POST call in error", response);
        this.openSnackBarUS();
    },
    () => {
        console.log("The POST observable is now completed.");
        this.openSnackBarS();
        location.reload();
    });
    
}

openSnackBarS() {
  this._snackBar.open("Successfully added.", "OK", {
    duration: 2000,
  });
}

openSnackBarUS() {
  this._snackBar.open("Error occurs!", "OK", {
    duration: 2000,
  });
}
}
