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
  uslov:boolean = false; //za toast
  subSuccess: string = "///"; //tekst poruke toast-a
  brojac : number = 0;

  ngOnInit() {

  }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  unsub(elem: DisplayOffer) {
 
    console.log(elem.id);
    this.subService.unsubscribe(elem.id);
    const index = this.dataSource.data.indexOf(elem);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
    
    this.subSuccess = "Successfully unsubscribed from offer";
    this.toastColor = "green-snackbar";
    this.uslov = true;
    this.cd.detectChanges();

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

  constructor(position: number,
    description: string,
    id: number,
    title: string) { }
}

// const ELEMENT_DATA: PeriodicElement[] = [
//   {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
//   {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
//   {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
//   {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
//   {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
//   {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
//   {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
//   {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
//   {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
//   {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
//   {position: 11, name: 'Sodium', weight: 22.9897, symbol: 'Na'},
//   {position: 12, name: 'Magnesium', weight: 24.305, symbol: 'Mg'},
//   {position: 13, name: 'Aluminum', weight: 26.9815, symbol: 'Al'},
//   {position: 14, name: 'Silicon', weight: 28.0855, symbol: 'Si'},
//   {position: 15, name: 'Phosphorus', weight: 30.9738, symbol: 'P'},
//   {position: 16, name: 'Sulfur', weight: 32.065, symbol: 'S'},
//   {position: 17, name: 'Chlorine', weight: 35.453, symbol: 'Cl'},
//   {position: 18, name: 'Argon', weight: 39.948, symbol: 'Ar'},
//   {position: 19, name: 'Potassium', weight: 39.0983, symbol: 'K'},
//   {position: 20, name: 'Calcium', weight: 40.078, symbol: 'Ca'},
// ];
