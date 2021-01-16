import { Injectable, NgModule } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { OfferInfoService } from '../services/offer-info.service'
import { MainOffersViewComponent } from '../components/main-offers-view/main-offers-view.component'
import { Offer, Page } from '../models/Offer'
import * as L from 'leaflet';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OfferModalComponent } from '../components/offer-modal/offer-modal.component';
import { title } from 'process';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  // private mainOffer: MainOffersViewComponent
  constructor(private http: HttpClient, private offerService: OfferInfoService, private modalService: NgbModal) { }
  // modal: OfferModalComponent = new OfferModalComponent(this.modalService);
  offers: Offer[];
  markers: L.Marker[] = new Array;

  // pinMarkers(map: L.Map, data: Page): void {
  //   this.offers = data["content"];
    
  //   for (const o of this.offers) {
  //     const lat = o.lat;
  //     const lon = o.lon;
  //     const marker = L.marker([lon, lat], {title: o.title+" " +o.place}).addTo(map)
  //     //.on('click', this.onClick);
  //     this.markers.push(marker);
  //   }
  // }

  // deleteMarkers(map: L.Map): void {
  //   console.log(this.markers.length)
  //   for (const marker of this.markers) {
  //     map.removeLayer(marker);
  //     this.markers = []
  //   }
    
  // }
  

  onClick(){
   
  }
}
