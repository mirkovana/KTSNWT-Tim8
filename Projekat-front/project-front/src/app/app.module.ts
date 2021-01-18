import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { OfferInfoComponent } from './components/offer-info/offer-info.component';
import { from } from 'rxjs';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
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
import { ValidateEmailComponent } from './components/validate-email/validate-email.component';
import { LoginComponent } from './components/login/login.component';
import { FilterComponent } from './components/filter/filter.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common'; 
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';


@NgModule({
  declarations: [
    AppComponent,
    OfferInfoComponent,
    MainOffersViewComponent,
    OfferTableItemComponent,
    MapComponent,
    OfferModalComponent,
    RegistrationComponent,
    NavigationComponent,
    ValidateEmailComponent,
    LoginComponent,
    FilterComponent


   

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
    ReactiveFormsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatCheckboxModule,
    MatIconModule,
    MatFormFieldModule,
    CommonModule,
    MatInputModule,
    MatButtonModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
