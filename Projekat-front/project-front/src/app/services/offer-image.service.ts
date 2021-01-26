import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferImagePage } from '../models/OfferImage';
// import { auth_token}  from '../models/app.constants'
import { auth_token}  from '../models/app.constants';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';

@Injectable({
  providedIn: 'root'
})
export class OfferImageService {

  constructor(
    private http: HttpClient
    //private authService: AuthService
  ) { }

  // auth_token = localStorage.getItem('token');

  getOfferImages(id: number): Observable<OfferImagePage> {
    return this.http.get<OfferImagePage>("http://localhost:8080/api/Offer-images/" + id + "/0/20");
  }

  getOfferImagePage(id:number, pageable: PaginatorPageable): Observable<Page1>{
    
    return this.http.get<Page1>("http://localhost:8080/api/Offer-images/" + id + "/" + pageable.pageIndex
     + "/" + pageable.pageSize );
}

  uploadImage(offerID: number, image: any, description: string) {
    const formData = new FormData();
    formData.append('image', image);
    formData.append('offerID', offerID.toString());
    formData.append('description', description);
    return this.http.post<any>('http://localhost:8080/api/Offer-images', formData, 
    { headers: new HttpHeaders().append('Authorization', `Bearer ${localStorage.getItem('token')}`) }
    )
      // .subscribe(data => {
      //   console.log(data);
      // })
      ;

  }

  updateDescription(offerID: number, imageID: number, description: string) {
    const formData = new FormData();
    formData.append('imageID', imageID.toString());
    formData.append('offerID', offerID.toString());
    formData.append('description', description);
    return this.http.put<any>('http://localhost:8080/api/Offer-images', formData, 
    { headers: new HttpHeaders().append('Authorization', `Bearer ${localStorage.getItem('token')}`) }
    )
  }


  deleteImage(imageID: number) {

    return this.http.delete<any>('http://localhost:8080/api/Offer-images/' + imageID, {
      headers: new HttpHeaders().append('Authorization', `Bearer ${localStorage.getItem('token')}`)

    })
  }
}
