import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '../../services/alert.service';
import { Subcategory } from 'src/app/models/Subcategory';
import { SubcategoryService } from '../../services/subcategory.service';
import { OfferService } from '../../services/offer.service';
import { forkJoin, Observable, Subscriber } from 'rxjs';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { query } from '@angular/animations';
import { GeolocationService } from 'src/app/services/geolocation.service';
import { rejects } from 'assert';
import { Offer } from 'src/app/models/Offer';
import { off } from 'process';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {
  subcategoryControl = new FormControl('', Validators.required);

  selectedItem: Subcategory;

  toastColor: string;
  subSuccess: string;
  cond: boolean = false;

  addOfferForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  offer: Offer = new Offer();
  public title: string='';
  public description: string='';
  public place: string='';

  subcategory1;
  subcategory$: Observable<Subcategory[]>;

  constructor(private subcategoryService: SubcategoryService, private offerService: OfferService, private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService,
    private geoService: GeolocationService,
    private cd: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.addOfferForm = this.formBuilder.group({
      title: [this.title, Validators.required],
      description: [this.description, Validators.required],
      place: [this.place, Validators.required] 
   });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    //this.subcategory$=this.subcategoryService.getAllSubcategories();

    this.subcategoryService.getAllSubcategories().subscribe((subcategory1) => {

      this.subcategory1 = subcategory1;
    });
  }

  get f() { return this.addOfferForm.controls; }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.addOfferForm.invalid) {
      return;
    }
    this.loading = true;
    this.offerService.addOffer(this.addOfferForm.value, this.selectedItem.id);
    window.location.replace("http://localhost:4200/home");

  }
  saveChangesEnabled() {
    return this.addOfferForm.value.title.length > 0 && this.selectedItem && this.addOfferForm.value.description.length > 0 && this.addOfferForm.value.place.length > 0;
  }


  add() {
    let offer = new Offer();
    offer.description = this.addOfferForm.get('description').value;
    offer.place = this.addOfferForm.get('place').value;
    offer.title = this.addOfferForm.get('title').value;


    this.geoService.getCityCoord(this.addOfferForm.get('place').value)
      .subscribe(data => {
        console.log(data.longt);
        console.log(data.latt);

        offer.lat = data.longt;
        offer.lon = data.latt;

        if (data.longt == 0.00000 || data.latt == 0.00000) {

          this.subSuccess = ("Place not found");
          this.toastColor = "red-snackbar";
          this.cond = true;
          this.cd.detectChanges();

          return;
        } else {
          this.cond = true;
          this.subSuccess = ("Place found");
          this.toastColor = "green-snackbar";
          this.cd.detectChanges();
        }
        this.offerService.addOffer(offer, 1).subscribe(
          (val) => {
            console.log("POST call successful value returned in body", val);
            this.router.navigate(['add-offer-image'], { queryParams: { offerID: val.id } });
          },

          response => {
            console.log("POST call in error", response);
          },
          () => {
            console.log("The POST observable is now completed.");
          }
        );

      }
        , (error) => {
          this.subSuccess = ("Error");
          this.toastColor = "red-snackbar";
          this.cond = true;
          this.cd.detectChanges();
          return;
        })







    // console.log("OTISAO")
    // let offerID = 1; // uzeti vracenog OfferDTO-a

  }
    //window.location.replace("http://localhost:4200/home");
    
   

}


