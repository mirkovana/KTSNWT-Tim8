import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GeolocationService {
  key: string = "756975461520733704966x10877 ";
  constructor(
    private http: HttpClient
  ) { }

  lon: number;
  lat: number;


  getCityCoord(city: string) {
    let params = new HttpParams().set("auth", this.key).set("locate", city).set('json', '1');
    return this.http.get<any>("https://geocode.xyz", { params: params });
  }
}
