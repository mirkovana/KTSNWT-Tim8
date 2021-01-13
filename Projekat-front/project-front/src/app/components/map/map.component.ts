import { AfterContentInit, Component } from '@angular/core';
import * as L from 'leaflet';
import { MapService } from '../../services/map.service';

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
export class MapComponent implements AfterContentInit {
  private map;
  private on: boolean;

  constructor(private mapService: MapService) { }

  //Da bi bili sigurni da je DOM kreiran i da mozemo da referenciramo komponentu
  ngAfterContentInit(): void {
    this.initMap();
    this.mapService.pinMarkers(this.map);
    this.on = true;
  }

  private initMap(): void {
    this.map = L.map('map').setView([44.014167, 20.911667], 7);

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });

    tiles.addTo(this.map);

  }

  public dodaj(): void {
    if (!this.on) {
      this.mapService.pinMarkers(this.map);
      this.on = true;
    }
  }

  public delete(): void {
    if (this.on) {
      this.mapService.deleteMarkers(this.map);
      this.on = false;
    }
  }

}
