import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OfferInfoService } from '../services/offer-info.service'
import { Offer } from '../models/Offer'
import * as L from 'leaflet';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  constructor(private http: HttpClient, private offerService: OfferInfoService) { }

  offers: Offer[];
  markers: L.Marker[] = new Array;

  pinMarkers(map: L.Map): void {
    this.offerService.getOffers().subscribe(data => {
      this.offers = data["content"];
      // console.log(this.offers);
      for (const o of this.offers) {
        const lat = o.lat;
        const lon = o.lon;
        const marker = L.marker([lon, lat]).addTo(map);
        this.markers.push(marker);
      }
      console.log(this.markers.length)
    });
  }

  deleteMarkers(map: L.Map):void {
    for(const marker of this.markers){
      map.removeLayer(marker);
    }
  }
}
