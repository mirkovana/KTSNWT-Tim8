import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import {Page, Post } from '../models/Post';
import { PaginatorPageable } from '../models/PaginatorPageable';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  bear  = localStorage.getItem("token");
    
  headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear, 'content-type': 'application/json'})
  constructor(private http: HttpClient, private _snackBar: MatSnackBar) { }


  getPostsPage(id, pageable: PaginatorPageable){
    
    return this.http.get<Page>("http://localhost:8080/api/posts/" + id + "?size=" + pageable.pageSize
     + "&page=" + pageable.pageIndex );
}
deletePost(postId: number){
  
  return this.http.delete("http://localhost:8080/api/posts/" + postId, {headers: this.headers}).subscribe(data=>{console.log(data);this.openSnackBarSD();location.reload();});
}


updatePost(post: Post) {
  const body=JSON.stringify(post);
  return this.http.put<any>('http://localhost:8080/api/posts/'+ post.id, body, {headers: this.headers}).subscribe(
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

 addPost(id:number, post:Post)  {
  const headers = { 'content-type': 'application/json'}  
  const body=JSON.stringify(post);
    console.log(body)
  
  return this.http.post<any>('http://localhost:8080/api/posts/'+id, body, {headers: this.headers}).subscribe(
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
