import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Offer } from 'src/app/models/Offer';
import { OfferInfoService } from 'src/app/services/offer-info.service';
import { SubscriptionService } from 'src/app/services/subscription.service';

@Component({
  selector: 'app-subscription-table',
  templateUrl: './subscription-table.component.html',
  styleUrls: ['./subscription-table.component.scss']
})
export class SubscriptionTableComponent implements AfterViewInit, OnInit {

  constructor(
    private offerService: OfferInfoService,
    private subService: SubscriptionService,
    private cd: ChangeDetectorRef
  ) { }

  displayedColumns: string[] = ['position', 'title', 'description', 'id'];
  // dataSource = new MatTableDataSource<PeriodicElement>(ELEMENT_DATA);
  dataSource: MatTableDataSource<DisplayOffer>;
  data: Offer[];
  display: DisplayOffer[] = new Array;

  toastColor: string;
  uslov: boolean = false; //za toast
  subSuccess: string = "///"; //tekst poruke toast-a
  brojac: number = 0;

  ngOnInit() {

  }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  unsub(elem: DisplayOffer) {

    console.log(elem.id);
    this.subService.unsubscribe(elem.id).subscribe(data => {
      const index = this.dataSource.data.indexOf(elem);
      this.dataSource.data.splice(index, 1);
      this.dataSource._updateChangeSubscription();

      this.subSuccess = "Successfully unsubscribed from offer";
      this.toastColor = "green-snackbar";
      this.uslov = true;
      this.cd.detectChanges();
    }, (error)=>{
      this.subSuccess = ("Error");
        this.toastColor = "red-snackbar";
        this.uslov = true;
        this.cd.detectChanges();
    }
    );;


    this.subSuccess = "";
    this.toastColor = "";
    this.uslov = false;
  }

  //inicijalizacija tebele
  ngAfterViewInit() {
    if (this.paginator.pageSize == undefined) {
      this.paginator.pageSize = 1;
    }
    if (this.paginator.pageIndex == undefined) {
      this.paginator.pageIndex = 0;
    }

    console.log(this.paginator.pageIndex);
    console.log(this.paginator.pageSize);
    this.offerService.getSubscriptions(this.paginator.pageIndex, this.paginator.pageSize).subscribe(data => {


      this.data = data.content;
      for (let index = 0; index < data.content.length; index++) {
        this.display.push({ position: index + 1, description: this.data[index].description, id: this.data[index].id, title: this.data[index].title });
        this.brojac++;
      }
      this.dataSource = new MatTableDataSource<DisplayOffer>(this.display);
      console.log("OVO : " + this.paginator.pageIndex);
      this.dataSource.paginator = this.paginator;
    })

  }
}

export class DisplayOffer {
  position: number;
  description: string;
  id: number;
  title: string;

  constructor(
    position: number,
    description: string,
    id: number,
    title: string) { }
}


