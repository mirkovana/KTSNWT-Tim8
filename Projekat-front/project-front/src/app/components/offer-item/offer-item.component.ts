import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-offer-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.scss']
})
export class OfferItemComponent implements OnInit {

  @Input() offer;

  constructor() { }

  ngOnInit(): void {
  }

  saveOfferId(id:number){
    console.log("ID OFERA JE:" + id );
    localStorage.setItem('offerId',JSON.stringify(id));
  }

}
