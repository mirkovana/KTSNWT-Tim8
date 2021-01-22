import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RatingService } from 'src/app/services/rating.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit {

  loggedIn = localStorage.getItem('username');
  //offerId = JSON.parse(localStorage.getItem('offerId')); //ovako bi trebalo
  //offerId = 1; // ovo ce se poslati iz offera
  
  _offerId = 0;

  @Input() set offerId(value){
    this._offerId = value;
    this.setUpRating();
  }
  starRating = 0;
  oldRating = 0;
  rated = false;
  updating = false;
  ratingId = 0;
  admin = false;
  
  constructor(private ratingService: RatingService, private _snackBar: MatSnackBar) { }

  openSnackBar(message) {
    this._snackBar.open(message, "Close", {
      duration: 2000,
      panelClass: ['blue-snackbar']
    });
  }

  setUpRating(){
    this.admin = this.loggedIn === "admin@nesto.com";
    //this.offerId = +localStorage.getItem('offerId');
    if (this.loggedIn){
      this.ratingService.getUsersRating(this._offerId).subscribe(data =>{
        if (data != null){
          this.rated = true;
          this.oldRating = data["rating"];
          this.starRating = this.oldRating;
          this.updating = false;
          this.ratingId = data['id'];
        }
      })
    }
  }

  ngOnInit(): void {
    // this.admin = this.loggedIn === "admin@nesto.com";
    // this.offerId = +localStorage.getItem('offerId');
    // if (this.loggedIn){
    //   this.ratingService.getUsersRating(this.offerId).subscribe(data =>{
    //     if (data != null){
    //       this.rated = true;
    //       this.oldRating = data["rating"];
    //       this.starRating = this.oldRating;
    //       this.updating = false;
    //       this.ratingId = data['id'];
    //     }
    //   })
    // }
  }

  ngOnChange(){

  }

  saveUpdate(){
    // namjestim nove vrijednosti, update = false
    this.ratingService.updateRating(this.ratingId, this.starRating).subscribe(data => {
      this.updating = false;
      this.openSnackBar("Rating updated.")
     
    })
  }

  deleteRating(){
    this.ratingService.deleteRating(this.ratingId).subscribe(()=>{
    this.rated = false;
    this.starRating = 0;})
    this.openSnackBar("Rating deleted.")
  }

  rateOffer(){
    this.ratingService.createRating(this._offerId, this.starRating).subscribe(data=>
      {
        this.rated = true;
        this.ratingId = data['id']
        this.openSnackBar("Rating created.")
      })
  }

}
