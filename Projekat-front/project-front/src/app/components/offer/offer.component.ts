import { Component, Input, OnInit } from '@angular/core';
import { OfferService } from '../../services/offer.service';
import {Offer} from '../../models/Offer';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {

  loggedIn = localStorage.getItem('username');
  broj:number;
  offer:Offer;
  editForm: FormGroup;

  constructor(public fb: FormBuilder, private offerService: OfferService) { 
  }

  ngOnInit(): void {
    //u htmlu sa !loggedIn je sve ono sto se prikazuje kad korisnik nije ulogovan uopste  
  if(this.loggedIn){
    console.log("ulogovan je neko");
    if(this.loggedIn==="admin@nesto.com"){this.broj=1;} //kad je ulogovan admin
    else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
  } 

  //POKUSAJ EDITA POCETAK
    this.offerService.getOfferById().subscribe(res => {this.offer=res;});

    this.editForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
  });
  }

  get f() { return this.editForm.controls; }

  submit() {
    console.log("blblblbl");
    console.log(this.editForm.value);
  }
  //POKUSAJ EDITA KRAJ

  deleteOffer(){
    this.offerService.deleteOffer(JSON.parse(localStorage.getItem('offerId')));
    window.location.replace("http://localhost:4200/home");
  }

}
