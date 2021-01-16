import '@angular/compiler'
import { Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Offer } from 'src/app/models/Offer';
import { HttpClient } from '@angular/common/http';
import { OfferImage, OfferImagePage } from 'src/app/models/OfferImage';
import { OfferImageService } from 'src/app/services/offer-image.service';
import { DomSanitizer } from '@angular/platform-browser';
import { IImage } from 'ng-simple-slideshow';
import { MatCarousel, MatCarouselComponent } from '@ngmodule/material-carousel';


@Component({
  selector: 'app-offer-modal',
  templateUrl: './offer-modal.component.html',
  styleUrls: ['./offer-modal.component.scss']
})
export class OfferModalComponent implements OnInit {//OnChanges,

  @Input() nesto: boolean;
  @Input() informacije: Offer;
  displayModal: boolean;
  starRating: number = 0;

  slides = [{ 'image': 'https://gsr.dev/material2-carousel/assets/demo.png' }, { 'image': 'https://gsr.dev/material2-carousel/assets/demo.png' }, { 'image': 'https://gsr.dev/material2-carousel/assets/demo.png' }, { 'image': 'https://gsr.dev/material2-carousel/assets/demo.png' }, { 'image': 'https://gsr.dev/material2-carousel/assets/demo.png' }];


  offerImages: OfferImagePage = new OfferImagePage(0, 0, []);

  constructor(private http: HttpClient, private offerImagesService: OfferImageService, private sanitizer: DomSanitizer) { };

  ngOnInit() {
    console.log("ON INIT" + " " + this.nesto);
  }
  rate(id:number){
    console.log(this.starRating);
    console.log(id);
  }

  ngOnChanges(changes: SimpleChange) {
    
    // console.log("USAO SAUSA")
    console.log("ID OFFERA: " + this.informacije.id)
    if (this.nesto) {
      // console.log("RADI BRE")
      this.displayModal = true;
      this.offerImagesService.getOfferImages(this.informacije.id).subscribe(data => {
        this.offerImages = data;
        data.content.forEach(element => {
          element.imageBase64 = 'data:image/jpg;base64,' + (this.sanitizer.bypassSecurityTrustResourceUrl(element.imageBase64) as any).changingThisBreaksApplicationSecurity;
        });
        let slika =
          console.log(data.content)
        console.log("BROJ SLIKA" + this.offerImages.content.length);
      });
    }
  }
  showModalDialog() {
    this.displayModal = true;
  }

}
