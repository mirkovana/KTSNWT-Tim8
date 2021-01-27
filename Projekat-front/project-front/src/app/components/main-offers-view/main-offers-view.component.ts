import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FilterParameters } from 'src/app/models/Filter';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { Offer, Page } from '../../models/Offer';
import { OfferInfoService } from '../../services/offer-info.service';

@Component({
  selector: 'app-main-offers-view',
  templateUrl: './main-offers-view.component.html',
  styleUrls: ['./main-offers-view.component.scss']
})
export class MainOffersViewComponent implements OnInit {
  
  offersPage: Page = new Page(0, 0, []);

  info = new PaginatorPageable(5000, 0, 10, 0);

  currentlyFiltered: FilterParameters;

  dataReady = false;

  constructor(private offerService: OfferInfoService) { 

  }

  
  ngOnInit(): void {
    
    this.offerService.getOffersPage(this.info).subscribe(data => {
      this.offersPage = data;
      this.info.length = this.offersPage.totalElements;
      console.log(this.offersPage);
      this.dataReady = true;
    });
  }

  filterClicked(filter: FilterParameters){     // literally clicked search
    console.log(filter + " clicked ")
    this.offerService.filterOffers2(filter, this.info).subscribe(data => {
      console.log(data)
      this.currentlyFiltered = filter;
      this.info.length = data.totalElements;
      this.offersPage = data;
    })
  }

  clearFilter(){
    this.currentlyFiltered = null;
    this.info.pageIndex = 0;
    this.offerService.getOffersPage(this.info).subscribe(data => {
      this.offersPage = data;
      this.info.length = this.offersPage.totalElements;
      console.log(this.offersPage);
      this.dataReady = true;
    });
  }

  onPageChange(event){
    this.info = event;
    console.log("currentyl filtered")
    console.log(this.currentlyFiltered);
    if (this.currentlyFiltered){
      let name = this.currentlyFiltered.name ? this.currentlyFiltered.name : "";
      let place = this.currentlyFiltered.place ? this.currentlyFiltered.place : "";
      
      this.offerService.filterOffers2({"name": name, "place": place, "subcats": this.currentlyFiltered.subcats},
      this.info).subscribe(data => {
        console.log(data)
        //this.currentlyFiltered = event;
        this.info.length = data.totalElements;
        this.offersPage = data;

      })
    }
    else {
      this.offerService.getOffersPage(this.info).subscribe(data =>{
        this.offersPage = data
      })
    }
  }
  
  
}
