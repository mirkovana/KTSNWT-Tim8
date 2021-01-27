import { Route } from '@angular/compiler/src/core';
import { ChangeDetectorRef, Component, OnInit, SimpleChange } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Offer } from 'src/app/models/Offer';
import { OfferService } from 'src/app/services/offer.service';
import { OfferImageService } from 'src/app/services/offer-image.service';
import { OfferImage, OfferImagePage } from 'src/app/models/OfferImage';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-edit-offer',
  templateUrl: './edit-offer.component.html',
  styleUrls: ['./edit-offer.component.scss']
})
export class EditOfferComponent implements OnInit {
  uploadForm: FormGroup;
  sub: any;
  length: number = 0;
  offerID: number;
  images: OfferImagePage = new OfferImagePage(0, 0, new Array);
  imagePaths: SafeResourceUrl[] = new Array;
  descPattern: string = "^[a-z0-9A-Z ]{1,20}$";
  uslov: boolean = false;

  toastColor: string;
  subSuccess: string;
  cond: boolean = false;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private route: ActivatedRoute,
    private _sanitizer: DomSanitizer,
    private offerImageService: OfferImageService
  ) {
    this.uploadForm = this.fb.group({
      description: ['']
    });
  }

  ngOnInit(): void {
    this.sub = this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        this.offerID = +params['offerID'] || 0;

        console.log('Query param page: ', this.offerID);
      });
    this.offerImageService.getOfferImages(this.offerID).subscribe(data => {
      this.images = data;
      // this.length = data.content;
      if (data.content.length != 0) {
        this.uslov = true;
      }
      data.content.forEach(element => {

        this.imagePaths.push('data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(element.imageBase64) as any).changingThisBreaksApplicationSecurity);
      });
    })
  }

  ngOnChanges(changes: SimpleChange) {
    this.offerImageService.getOfferImages(this.offerID).subscribe(data => {
      this.images = data;
      // this.length = data.content;
      if (data.content.length != 0) {
        this.uslov = true;
      }
      data.content.forEach(element => {

        this.imagePaths.push('data:image/jpg;base64,' + (this._sanitizer.bypassSecurityTrustResourceUrl(element.imageBase64) as any).changingThisBreaksApplicationSecurity);
      });
    })
  }

  submit(id: number) {
    this.cond = false;
    console.log(id);
    let validator = Validators.pattern("[A-Za-z0-9,.]*");
    if (this.uploadForm.get('description').value == "") {
      this.subSuccess = ("Description is required.");
      this.toastColor = "red-snackbar";
      this.cond = true;
      this.cd.detectChanges();
      return;
    }
    if (!this.uploadForm.get('description').value.match(this.descPattern)) {
      this.subSuccess = ("Invalid image description (only numbers and letters are allowed, 20 characters max)");
      this.toastColor = "red-snackbar";
      this.cond = true;
      this.cd.detectChanges();
      return;
    }


    this.offerImageService.updateDescription(this.offerID, id,
      this.uploadForm.get('description').value)
      .subscribe(data => {
        this.images.content.forEach(element=>{
          if(element.id == id){
            element.description =  this.uploadForm.get('description').value;
          }
        })
        this.cond = true;
        this.subSuccess = ("Description successfully changed");
        this.toastColor = "green-snackbar";
        this.cd.detectChanges();
      },
        (error) => {                              //Error callback
          console.error('error caught in component')
          this.subSuccess = ("Error");
          this.toastColor = "red-snackbar";
          this.cond = true;
          this.cd.detectChanges();
        });
  }

  deleteImage(imageID: number) {
    console.log("DELETE IMAGE");
    this.offerImageService.deleteImage(imageID).subscribe(data => {
      console.log(data);
      let image: OfferImage = new OfferImage();
      image.id = imageID;
      let index = this.images.content.indexOf(image);
      this.images.content.splice(index, 1);
      this.uslov = true;
      this.cond = true;
      this.subSuccess = ("Image successfully deleted");
      this.toastColor = "green-snackbar";
      this.cd.detectChanges();
      this.cd.detectChanges();
    }, (error) => {
      console.error('error caught in component')
      this.subSuccess = ("Error image not deleted");
      this.toastColor = "red-snackbar";
      this.cond = true;
      this.cd.detectChanges();
    }
    )

  }


  addImage() {
    console.log(this.sub)
    this.router.navigate(['add-offer-image/'], { queryParams: { offerID: this.offerID } });
  }
}
