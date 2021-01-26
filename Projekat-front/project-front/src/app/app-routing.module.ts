import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditOfferComponent } from './components/edit-offer/edit-offer.component';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { MainSubscriptionsViewComponent } from './components/main-subscriptions-view/main-subscriptions-view.component';
import { MapComponent } from './components/map/map.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ValidateEmailComponent } from './components/validate-email/validate-email.component';
import { LoginComponent } from './components/login/login.component';
import { OfferComponent } from './components/offer/offer.component';
import { AddOfferComponent } from './components/add-offer/add-offer.component';
import { AddOfferImageComponent } from './components/add-offer-image/add-offer-image.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: MainOffersViewComponent },
  { path: 'subscriptions', component: MainSubscriptionsViewComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'validateEmail', component: ValidateEmailComponent },
  { path: 'edit-offer', component: EditOfferComponent },
  { path: 'login', component: LoginComponent },
  { path: 'offer', component: OfferComponent }, // ovde treba da ide id jer ce se na osnovu njega dobaviti offer
  { path: 'addOffer', component: AddOfferComponent },
  { path: 'add-offer-image', component: AddOfferImageComponent},
  { path: 'offers/:id', component: OfferComponent }, // ovde treba da ide id jer ce se na osnovu njega dobaviti offer

];//component: MapComponent



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
