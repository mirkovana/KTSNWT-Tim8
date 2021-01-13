import { AfterContentInit, Component } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterContentInit {
  private map: any;

  constructor() { }

  //Da bi bili sigurni da je DOM kreiran i da mozemo da referenciramo komponentu
  ngAfterContentInit(): void {
    this.initMap();
  }

  private initMap(): void {
    this.map = L.map('map').setView([45.350833, 19.441333], 15);

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

  }

}
