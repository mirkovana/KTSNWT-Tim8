import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { MapInfoComponent } from './components/map-info/map-info.component';
import { OfferInfoComponent } from './components/offer-info/offer-info.component';
import { from } from 'rxjs';
import { MainOffersViewComponent } from './main-offers-view/main-offers-view.component';
import { FilterComponent } from './filter/filter.component';
import { OfferTableItemComponent } from './offer-table-item/offer-table-item.component';




@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    MapInfoComponent,
    OfferInfoComponent,
    MainOffersViewComponent,
    FilterComponent,
    OfferTableItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
