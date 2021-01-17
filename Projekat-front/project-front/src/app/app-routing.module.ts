import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EditOfferComponent } from './components/edit-offer/edit-offer.component';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { MainSubscriptionsViewComponent } from './components/main-subscriptions-view/main-subscriptions-view.component';
import { MapComponent } from './components/map/map.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ValidateEmailComponent } from './components/validate-email/validate-email.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: MainOffersViewComponent },
  { path: 'subscriptions', component: MainSubscriptionsViewComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'validateEmail', component: ValidateEmailComponent },
  { path: 'edit-offer', component: EditOfferComponent}
];//component: MapComponent

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
