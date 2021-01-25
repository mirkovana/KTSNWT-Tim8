import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, tap, take } from 'rxjs/operators';
import { Page, PageGen } from "../models/Offer";
import { PaginatorPageable } from "../models/PaginatorPageable";

@Injectable({providedIn: 'root'})
export class CommentService{

    constructor(private http: HttpClient){}

    url: string = "http://localhost:8080/api/comments/";

    bear  = localStorage.getItem("token");
    
    headers: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + this.bear})



    setupHeader(){

        this.bear = localStorage.getItem("token");
        this.headers = new HttpHeaders({"Authorization": "Bearer " + this.bear})
        
    }

    getCommentsFromOffer(id: number, size, page){
        return this.http.get<PageGen>("http://localhost:8080/api/comments/" + id + "?size="
        + size + "&page=" + page).pipe(map(response => {
            return response;
        }));//.subscribe(data => {
        //    console.log(data);
        //})
    }

    getCommentsPage(nesto: PaginatorPageable, id: number){
        console.log("get offers page + " + nesto.pageSize + " " + nesto.pageIndex);
        return this.http.get<PageGen>("http://localhost:8080/api/comments/" + id+ "?size=" + nesto.pageSize
         + "&page=" + nesto.pageIndex);
    }

    updateComment(commentId: number, text: string, image: Blob){
        this.setupHeader();
        const data = new FormData();
        data.append("text", text);
        data.append("image", image)
        data.append("commentId", "" + commentId)
        return this.http.put(this.url, data, {headers: this.headers});
    }

    createComment(offerId: number, text: string, image: Blob){
        this.setupHeader();
        let bear  = localStorage.getItem("token");
        let headers1: HttpHeaders = new HttpHeaders({"Authorization": "Bearer " + bear})
        const data = new FormData();
        console.log(text + " tekst ");
        data.append("text", text);
        data.append("image", image)
        data.append("offerId", "" + offerId)
        return this.http.post(this.url, data, {headers: headers1});
    }

    deleteComment(commentId: number){
        this.setupHeader();
        return this.http.delete("http://localhost:8080/api/comments/" + commentId, {headers: this.headers})
    }

}