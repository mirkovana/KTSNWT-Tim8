import { Component, OnInit } from '@angular/core';
import { Offer } from '../../models/Offer';
import { OfferInfoService } from '../../services/offer-info.service'

@Component({
  selector: 'app-offer-info',
  templateUrl: './offer-info.component.html',
  styleUrls: ['./offer-info.component.scss']
})
export class OfferInfoComponent implements OnInit {
  offers: Offer[];

  constructor(private service: OfferInfoService) {}

  ngOnInit(): void {
    //this.offers = this.service.getOffers();
    this.service.getOffers().subscribe(data => {
      this.offers = data["content"];
      console.log(this.offers);
    });
  }

}
