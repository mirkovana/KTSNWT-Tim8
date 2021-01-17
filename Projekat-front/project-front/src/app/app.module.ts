import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { OfferInfoComponent } from './components/offer-info/offer-info.component';
import { from } from 'rxjs';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { FilterComponent } from './filter/filter.component';
import { OfferTableItemComponent } from './components/offer-table-item/offer-table-item.component';
import { OfferModalComponent } from './components/offer-modal/offer-modal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {AccordionModule} from 'primeng/accordion';     //accordion and accordion tab
import {MenuItem} from 'primeng/api'; 
import {DialogModule} from 'primeng/dialog';
import {ButtonModule} from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCarouselModule } from '@ngmodule/material-carousel';
import { RegistrationComponent } from './components/registration/registration.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import {ReactiveFormsModule,FormsModule} from '@angular/forms';
import { ValidateEmailComponent } from './components/validate-email/validate-email.component'



@NgModule({
  declarations: [
    AppComponent,
    OfferInfoComponent,
    MainOffersViewComponent,
    FilterComponent,
    OfferTableItemComponent,
    MapComponent,
    OfferModalComponent,
    RegistrationComponent,
    NavigationComponent,
    ValidateEmailComponent,


   

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    AccordionModule,
    DialogModule,
    ButtonModule,
    BrowserAnimationsModule,
    MatCarouselModule,
    ReactiveFormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
