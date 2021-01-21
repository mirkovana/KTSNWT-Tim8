import { Component, Input, OnInit } from '@angular/core';
import { OfferService } from '../../services/offer.service';
import {Page} from '../../models/Post';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { PostService } from 'src/app/services/post.service';
import {MatDialog} from '@angular/material/dialog';
import {AddPostComponent} from 'src/app/components/add-post/add-post.component'

@Component({
  selector: 'app-offer',
  templateUrl: './offer.component.html',
  styleUrls: ['./offer.component.scss']
})
export class OfferComponent implements OnInit {

  offersPage: Page = new Page(0, 0, []);

  info = new PaginatorPageable(5000, 0, 10, 0);
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

  constructor(public dialog: MatDialog, public formBuilder: FormBuilder, private offerService: OfferService, private snackbar: MatSnackBar, private postService: PostService) { 
  }
 
  ngOnInit(): void {
    //u htmlu sa !loggedIn je sve ono sto se prikazuje kad korisnik nije ulogovan uopste  
    if(this.loggedIn){
      console.log("ulogovan je neko");
      if(this.loggedIn==="admin@nesto.com"){this.broj=1;this.admin=true;} //kad je ulogovan admin
      else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
    } 

    this.postService.getPostsPage(this.info).subscribe(data => {
      this.offersPage = data;
      this.info.length = this.offersPage.totalElements;
      //console.log(this.offersPage);
      //this.dataReady = true;
    });
    //POKUSAJ EDITA POCETAK
    this.offerService.getOfferById().subscribe(res => {this.offer=res;});
  }

  saveChangesEnabled() {
    return this.offer.title.length > 0 && this.offer.description.length > 0;
  }

  saveChanges() {
     this.offerService.updateOffer(this.offer);
  }

  deleteOffer(){
    this.offerService.deleteOffer(JSON.parse(localStorage.getItem('offerId')));
    window.location.replace("http://localhost:4200/home");
  }


  onPageChange(event){
    this.info = event;
    console.log("currentyl filtered")
   
      this.postService.getPostsPage(this.info).subscribe(data =>{
        this.offersPage = data;
      })
    }
  
    openDialog(): void {
      const dialogRef = this.dialog.open(AddPostComponent,{
        width: '640px',disableClose: true 
      });
  }
}





