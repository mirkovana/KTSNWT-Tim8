import { AfterContentInit, Component, Input } from '@angular/core';
import * as L from 'leaflet';
import { Offer } from 'src/app/models/Offer';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterContentInit {
  private map: any;

  @Input() offers: Offer[] = []; 

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
