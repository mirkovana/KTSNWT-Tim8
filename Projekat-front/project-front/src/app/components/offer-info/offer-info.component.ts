import { Component, Input, OnInit } from '@angular/core';
import { Offer } from '../../models/Offer';
import { OfferInfoService } from '../../services/offer-info.service'

@Component({
  selector: 'app-offer-info',
  templateUrl: './offer-info.component.html',
  styleUrls: ['./offer-info.component.scss']
})
export class OfferInfoComponent implements OnInit {
  @Input() page;
  
  constructor(private service: OfferInfoService) {}

  ngOnInit(): void {
  }

}
