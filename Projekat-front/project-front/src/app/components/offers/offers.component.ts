import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/models/Offer';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { OfferInfoService } from 'src/app/services/offer-info.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.scss']
})
export class OffersComponent implements OnInit {
  @Input() page: Page;
  @Input() paginatorDetails: PaginatorPageable;

  //*********************//
  @Output() pageChanged = new EventEmitter<PaginatorPageable>();

  constructor(private offersService:OfferInfoService) { }

  ngOnInit(): void {
  }

  getData(event){
    console.log(event);
  }

  //************************************************ */
  onClick(event){
    //console.log(event)
    this.pageChanged.emit(event);

    //this.offersService.emitChildEvent(event);
  }


}
