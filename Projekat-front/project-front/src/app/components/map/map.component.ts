import { AfterContentInit, Component, Input } from '@angular/core';
import * as L from 'leaflet';
import { Offer } from 'src/app/models/Offer';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterContentInit {
  private map;
  private on: boolean;

  @Input() offers: Offer[] = []; 

  constructor() { }

  //Da bi bili sigurni da je DOM kreiran i da mozemo da referenciramo komponentu
  ngAfterContentInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = L.map('map').setView([44.014167, 20.911667], 7);

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

  }

  // public dodaj(): void {
  //   if (!this.on) {
  //     this.mapService.pinMarkers(this.map);
  //     this.on = true;
  //   }
  // }

  // public delete(): void {
  //   if (this.on) {
  //     this.mapService.deleteMarkers(this.map);
  //     this.on = false;
  //   }
  // }

}
