import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AlertService } from '../../services/alert.service';
import { Subcategory } from 'src/app/models/Subcategory';
import { SubcategoryService } from '../../services/subcategory.service';
import { OfferService } from '../../services/offer.service';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.scss']
})
export class AddOfferComponent implements OnInit {
  subcategoryControl = new FormControl('', Validators.required);

  selectedItem:Subcategory;

  addOfferForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  subcategory1;
  subcategory$:Observable<Subcategory[]>;

  constructor(private subcategoryService: SubcategoryService, private offerService: OfferService, private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private alertService: AlertService) { }

  ngOnInit(): void {
    this.addOfferForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      place: ['', Validators.required] 
   });

  // get return url from route parameters or default to '/'
  this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';

    //this.subcategory$=this.subcategoryService.getAllSubcategories();

    this.subcategoryService.getAllSubcategories().subscribe((subcategory1)=>{
      
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
    //window.location.replace("http://localhost:4200/home");
    
}
saveChangesEnabled() {
  return this.addOfferForm.value.title.length > 0 && this.selectedItem && this.addOfferForm.value.description.length >0 && this.addOfferForm.value.place.length >0;
}
}
