import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OfferInfoService {

  constructor() { }

  getOffers(){
    return [
      {
        id: 1,
        avgRating: 4.5,
        description: "PRVI OPIS",
        lat: 39.0,
        lon: 32.0,
        nmbOfRatings: 10,
        title: "PRVI NASLOV",
        place: "Novi Sad"
      },
      {
        id: 2,
        avgRating: 3.5,
        description: "DRUGI OPIS",
        lat: 29.0,
        lon: 22.0,
        nmbOfRatings: 20,
        title: "DRUGI NASLOV",
        place: "Mostar"
      },
      {
        id: 3,
        avgRating: 2.0,
        description: "TRECI OPIS",
        lat: 80.0,
        lon: 92.0,
        nmbOfRatings: 40,
        title: "TRECI NASLOV",
        place: "Beograd"
      },
      {
        id: 4,
        avgRating: 2.0,
        description: "CETVRTI OPIS",
        lat: 80.0,
        lon: 92.0,
        nmbOfRatings: 40,
        title: "CETVRTI NASLOV",
        place: "Nis"
      },
      {
        id: 5,
        avgRating: 2.0,
        description: "PETI OPIS",
        lat: 80.0,
        lon: 92.0,
        nmbOfRatings: 40,
        title: "PETI NASLOV",
        place: "Kragujevac"
      },
    ]
  }
}
