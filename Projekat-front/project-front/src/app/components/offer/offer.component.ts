import { Component, Input, OnInit } from '@angular/core';
import { OfferService } from '../../services/offer.service';
import {Offer} from '../../models/Offer';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {

  offer = {
    title: '',
    description: ''
  }

  admin:boolean=false;

  editedTitle:string;

  loggedIn = localStorage.getItem('username');
  broj:number;
  //offer:Offer;
  editOfferForm: FormGroup;
  submitted = false;

  constructor(public formBuilder: FormBuilder, private offerService: OfferService, private snackbar: MatSnackBar) { 
  }

  ngOnInit(): void {
    //u htmlu sa !loggedIn je sve ono sto se prikazuje kad korisnik nije ulogovan uopste  
    if(this.loggedIn){
      console.log("ulogovan je neko");
      if(this.loggedIn==="admin@nesto.com"){this.broj=1;this.admin=true;} //kad je ulogovan admin
      else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
    } 

    //POKUSAJ EDITA POCETAK
    this.offerService.getOfferById().subscribe(res => {this.offer=res;});
  }

  saveChangesEnabled() {
    return this.offer.title.length > 0 && this.offer.description.length > 0;
  }

  saveChanges() {
    console.log("PRITISNUTO DUGME EDIT");
    this.offerService.updateOffer(this.offer);
  }

  deleteOffer(){
    this.offerService.deleteOffer(JSON.parse(localStorage.getItem('offerId')));
    window.location.replace("http://localhost:4200/home");
  }

}
