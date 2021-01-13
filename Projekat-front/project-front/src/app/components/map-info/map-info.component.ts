import { Component, OnInit, Input } from '@angular/core';
import { Offer } from 'src/app/models/Offer';

@Component({
  selector: 'app-map-info',
  templateUrl: './map-info.component.html',
  styleUrls: ['./map-info.component.scss']
})
export class MapInfoComponent implements OnInit {
  @Input() offer: Offer;

  constructor() { 
    // this.offer = new Offer
    // this.offer.id = 213;
  }

  ngOnInit(): void {
  }

}
