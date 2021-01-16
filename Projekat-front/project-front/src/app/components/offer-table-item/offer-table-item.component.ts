import { Component, Input, OnInit } from '@angular/core';
import { Offer } from '../../models/Offer';

@Component({
  selector: 'app-offer-table-item',
  templateUrl: './offer-table-item.component.html',
  styleUrls: ['./offer-table-item.component.scss']
})
export class OfferTableItemComponent implements OnInit {

  @Input() offer: Offer;

  constructor() { }

  ngOnInit(): void {
  }

}
