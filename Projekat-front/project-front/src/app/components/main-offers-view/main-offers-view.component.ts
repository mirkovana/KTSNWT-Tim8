import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Offer, Page } from '../../models/Offer';
import { OfferInfoService } from '../../services/offer-info.service';

@Component({
  selector: 'app-main-offers-view',
  templateUrl: './main-offers-view.component.html',
  styleUrls: ['./main-offers-view.component.scss']
})
export class MainOffersViewComponent implements OnInit {
  
  offersPage: Page = new Page(0, 0, []);

  constructor(private offerInfoService: OfferInfoService) { 

  }

  ngOnInit() {
    this.offerInfoService.getOffers().subscribe(data => {
      this.offersPage = data;
  });    
  }

  
  
}
