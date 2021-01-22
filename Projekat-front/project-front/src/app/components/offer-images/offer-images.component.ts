import { Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { OfferImagePage, Page1} from 'src/app/models/OfferImage';
import { ActivatedRoute, Params } from '@angular/router';
import { OfferImageService } from 'src/app/services/offer-image.service';

@Component({
  selector: 'app-offer-images',
  templateUrl: './offer-images.component.html',
  styleUrls: ['./offer-images.component.scss']
})
export class OfferImagesComponent implements OnInit {
  offerId=0;
  @Input() page: Page1;
  @Input() paginatorDetails: PaginatorPageable;
  @Output() pageChanged = new EventEmitter<PaginatorPageable>();

  constructor(private route: ActivatedRoute, private offerImageService: OfferImageService) { }

  ngOnInit(): void {
    this.route.params
    .subscribe(
      (params: Params) => {
        this.offerId = +params['id'];
        this.offerImageService.getOfferImagePage(this.offerId, this.paginatorDetails).subscribe(data => {
          this.page = data;
          this.paginatorDetails.length = data.totalElements;
        })
      }
    );
  }

  onClick(event){
    //console.log(event)
    this.pageChanged.emit(event);

    //this.offersService.emitChildEvent(event);
  }

}
