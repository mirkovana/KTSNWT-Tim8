import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-offer-image',
  templateUrl: './add-offer-image.component.html',
  styleUrls: ['./add-offer-image.component.scss']
})
export class AddOfferImageComponent implements OnInit {
  sub:any;
  offerID:number;
  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.sub = this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        this.offerID = +params['offerID'] || 0;

        console.log('Query param page: ', this.offerID);
      });
  }


}
