import { Component, OnInit } from '@angular/core';
import { RatingService } from 'src/app/services/rating.service';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent implements OnInit {

  loggedIn = localStorage.getItem('username');
  //offerId = JSON.parse(localStorage.getItem('offerId')); //ovako bi trebalo
  offerId = 1; // ovo ce se poslati iz offera
  starRating = 0;
  oldRating = 0;
  rated = false;
  updating = false;
  ratingId = 0;

  constructor(private ratingService: RatingService) { }

  ngOnInit(): void {
    if (this.loggedIn){
      this.ratingService.getUsersRating(this.offerId).subscribe(data =>{
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

  rate(id: number) {
    console.log("RATING: " + this.starRating);
    console.log("ID: " + id);
    /*if(this.oldRating == 0 && this.starRating != 0){
      this.offerService.sendRating(id, this.starRating);
      this.oldRating = this.starRating;
    }
    else{
      if(this.oldRating != this.starRating){
        this.offerService.updateRating(id, this.starRating);
        this.oldRating = this.starRating;
      }
    }*/
    // this.starRating = 0;
  }

  saveUpdate(){
    console.log(this.saveUpdate + " " + this.starRating)
    // namjestim nove vrijednosti, update = false
    this.ratingService.updateRating(this.ratingId, this.starRating).subscribe(data => {
      console.log("poslat put zahtjev")
      console.log(data)
      this.updating = false;
     
    })
  }

  deleteRating(){
    console.log(this.ratingId + " rejting id ")
    // stavim da nije rated i sta rating na 0
    this.ratingService.deleteRating(this.ratingId).subscribe(()=>{
    this.rated = false;
    this.starRating = 0;
    console.log("obrisanoo")})
  }

  rateOffer(){
    console.log(this.starRating + " novoooo")
    this.ratingService.createRating(this.offerId, this.starRating).subscribe(data=>
      {
        console.log("kreirano");
        console.log("rating");
        console.log(data)
        this.rated = true;
        this.ratingId = data['id']
        
      })
    // provjeriti jel 0
    // u sub da namjestim da je rated i da postavim sve opet

  }

}
