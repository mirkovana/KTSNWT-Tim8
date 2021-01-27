import '@angular/compiler'
import { ChangeDetectorRef, Component, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Offer } from 'src/app/models/Offer';
import { HttpClient } from '@angular/common/http';
import { OfferImage, OfferImagePage } from 'src/app/models/OfferImage';
import { OfferImageService } from 'src/app/services/offer-image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { IImage } from 'ng-simple-slideshow';
import { MatCarousel, MatCarouselComponent } from '@ngmodule/material-carousel';
import { OfferInfoService } from 'src/app/services/offer-info.service';
import { SubscriptionService } from 'src/app/services/subscription.service';
import { EventEmitter } from 'events';
import { Router } from '@angular/router';


@Component({
  selector: 'app-offer-modal',
  templateUrl: './offer-modal.component.html',
  styleUrls: ['./offer-modal.component.scss']
})
export class OfferModalComponent implements OnInit {//OnChanges,

  @Input() nesto: boolean; // uslov
  @Input() informacije: Offer; //Offer koju prikazujemo
  loggedIn = localStorage.getItem('username');
  displayModal: boolean;
  starRating: number = 0;
  oldRating: number = 0;
  toastColor: string;
  uslov: boolean = false; //za toast
  subSuccess: string = "///"; //Tekst poruke toast-a
  broj:number;

  offerImages: OfferImagePage = new OfferImagePage(0, 0, []);

  constructor(
    private http: HttpClient,
    private offerImagesService: OfferImageService,
    private sanitizer: DomSanitizer,
    private offerService: OfferInfoService,
    private subService: SubscriptionService,
    private cd: ChangeDetectorRef,
    private router: Router
  ) { };

  ngOnInit() {
    if(this.loggedIn){
      console.log("ulogovan je neko");
      if(this.loggedIn==="admin@nesto.com"){this.broj=1;} //kad je ulogovan admin
      else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
    } 
  }
  // rate(id: number) {

  // }

  ngOnChanges(changes: SimpleChange) {
    if (this.nesto) {
      this.displayModal = true;
      this.offerImagesService.getOfferImages(this.informacije.id).subscribe(data => {
        this.offerImages = data;
        data.content.forEach(element => {
          element.imageBase64 = 'data:image/jpg;base64,' + (this.sanitizer.bypassSecurityTrustResourceUrl(element.imageBase64) as any).changingThisBreaksApplicationSecurity;
        });

      });
    }
  }

  subscribe(o: Offer) {
    this.subSuccess = "";
    this.toastColor = "";
    this.uslov = false;
    console.log(o.id);
    this.subService.subscribe(o.id).subscribe(data => {
      this.uslov = true;
      this.subSuccess = ("Successfully subscribed to offer");
      this.toastColor = "green-snackbar";
      this.cd.detectChanges();
    }, (error) => {                              //Error callback
      console.error('error caught in component')
      if (error.status == 400) {
        this.subSuccess = ("You are allready subscribed to this offer");
        this.toastColor = "red-snackbar";
        this.uslov = true;
        this.cd.detectChanges();
      } else {
        this.subSuccess = ("Error");
        this.toastColor = "red-snackbar";
        this.uslov = true;
        this.cd.detectChanges();
      }
    })

  }

  edit(offerID: number) {
    this.router.navigate(['edit-offer/'], { queryParams: { offerID: offerID } });

  }

}
