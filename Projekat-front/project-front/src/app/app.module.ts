import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { MapInfoComponent } from './components/map-info/map-info.component';
import { OfferInfoComponent } from './components/offer-info/offer-info.component';
import { from } from 'rxjs';




@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    MapInfoComponent,
    OfferInfoComponent
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
