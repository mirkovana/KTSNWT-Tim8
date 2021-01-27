import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as L from 'leaflet';
import { Offer, Page } from 'src/app/models/Offer';
import { OfferInfoService } from 'src/app/services/offer-info.service';
// import { MapService } from '../../services/map.service';
import { OfferModalComponent } from '../offer-modal/offer-modal.component';

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})


export class MapComponent implements OnChanges, AfterContentInit, OnInit {

  @Input() offers: Page;
  uslov: boolean = false; // uslov za dodavanja i uklanjanje markera sa mape
  offerInfo: Offer = new Offer(); //slanje offera modal-u

  alloffers: Offer[]; // napravljena lista od offers: Page

  markers: L.Marker[] = new Array;

  map;
  on: boolean; // uslov za dodavanje ili brisanje

  // @ViewChild('content') content: OfferModalComponent;

  constructor(
    private modalService: NgbModal,
    private cd: ChangeDetectorRef
  ) { }
  ngOnInit(): void {
    this.uslov = false;

  }

  //Da bi bili sigurni da je DOM kreiran i da mozemo da referenciramo komponentu
  ngOnChanges(): void {

    if (this.offers && this.map) {
      this.pinMarkers(this.map, this.offers);
      this.on = true;
    }
    this.offerService.offerChosenEvent.subscribe(data => {
      this.uslov = true;
      this.offerInfo = data;
      this.cd.detectChanges();
      //this.offerInfo = null;
      this.uslov = false;
     
    })
  }

  ngAfterContentInit(): void {
    this.initMap();
  }

  private initMap(): void {

    this.map = L.map('map').setView([44.014167, 20.911667], 2);

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

  }

  public dodaj(): void {

    if (!this.on) {
      this.pinMarkers(this.map, this.offers);
      this.on = true;
    }

  }

  public delete(): void {
    if (this.on) {
      this.deleteMarkers(this.map);
      this.on = false;
    }
  }

  pinMarkers(map: L.Map, data: Page): void { //dodavanje markera
    this.deleteMarkers(map);
    this.alloffers = data["content"];
    let id: number = 0;
    for (const o of this.alloffers) {

      const lat = o.lat;
      const lon = o.lon;
      // let off = new L.customID(o.id, o.title, o.description, o.avgRating, o.nmbOfRatings, o.lat, o.lon, o.place); //o.id, o.title, o.description, o.avgRating, o.nmbOfRatings, o.lat, o.lon, o.place
      const marker = L.marker([lon, lat], { customID: o, title: o.title }).addTo(map).on('click', this.onClick);
      // const popup = L.popup().setLatLng([lon, lat]).setContent(o.title).addTo(map);
      this.markers.push(marker);

      id = id + 1;
    }
  }


  onClick = (e) => { //otvaranje modala
    this.uslov = true;
    this.offerInfo = e.sourceTarget.options.customID;
    this.cd.detectChanges();
    // console.log(e.sourceTarget.options.customID);
    this.uslov = false;
  }

  deleteMarkers(map: L.Map): void {  // uklanjanje svih markera
    // console.log(this.markers.length);
    for (const marker of this.markers) {
      map.removeLayer(marker);
      this.markers = []
    }

  }
}
