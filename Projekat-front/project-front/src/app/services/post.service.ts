import { Injectable } from '@angular/core';
import { HttpClient , HttpHeaders} from '@angular/common/http';
import {Page, Post } from '../models/Post';
import { PaginatorPageable } from '../models/PaginatorPageable';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  bear  = localStorage.getItem("token");
    
  headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear, 'content-type': 'application/json'})
  constructor(private http: HttpClient) { }


  getPostsPage(pageable: PaginatorPageable){
    
    return this.http.get<Page>("http://localhost:8080/api/posts/" + localStorage.getItem("offerId") + "?size=" + pageable.pageSize
     + "&page=" + pageable.pageIndex );
}
deletePost(postId: number){
  
  return this.http.delete("http://localhost:8080/api/posts/" + postId, {headers: this.headers}).subscribe(data=>{console.log(data);});
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
    },
    () => {
        console.log("The PUT observable is now completed.");
    });

 }

 addPost(post:Post)  {
  const headers = { 'content-type': 'application/json'}  
  const body=JSON.stringify(post);
    console.log(body)
  
  return this.http.post<any>('http://localhost:8080/api/posts/'+localStorage.getItem("offerId"), body, {headers: this.headers}).subscribe(
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
