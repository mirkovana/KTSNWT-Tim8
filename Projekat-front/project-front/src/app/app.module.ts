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
import { NgbModule, NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import {AccordionModule} from 'primeng/accordion';     //accordion and accordion tab
import {MenuItem, SharedModule} from 'primeng/api'; 
import {DialogModule} from 'primeng/dialog';
import {ButtonModule} from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCarouselModule } from '@ngmodule/material-carousel';
import { MainSubscriptionsViewComponent } from './components/main-subscriptions-view/main-subscriptions-view.component';
import { SubscriptionTableComponent } from './components/subscription-table/subscription-table.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ToastComponent } from './components/toast/toast.component';
import { MatFormFieldModule  } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { ValidateEmailComponent } from './components/validate-email/validate-email.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { RegistrationComponent } from './components/registration/registration.component';


@NgModule({
  declarations: [
    AppComponent,
    OfferInfoComponent,
    MainOffersViewComponent,
    FilterComponent,
    OfferTableItemComponent,
    MapComponent,
    OfferModalComponent,
    MainSubscriptionsViewComponent,
    SubscriptionTableComponent,
    ToastComponent,
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
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatInputModule,
    ReactiveFormsModule
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
