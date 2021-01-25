import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Offer } from 'src/app/models/Offer';
import { OfferInfoService } from 'src/app/services/offer-info.service';

@Component({
  selector: 'app-offer-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.scss']
})
export class OfferItemComponent implements OnInit {

  @Input() offer;

  constructor(private offerService: OfferInfoService) { }

  ngOnInit(): void {
  }

  viewOnMap(){
    //this.viewOnMapEvent.emit(this.offer);
    this.offerService.offerChosenEvent.next(this.offer);
  }


}
