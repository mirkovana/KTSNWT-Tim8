import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { MainSubscriptionsViewComponent } from './components/main-subscriptions-view/main-subscriptions-view.component';
import { MapComponent } from './components/map/map.component';

const routes: Routes = [ 
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: MainOffersViewComponent},
  { path: 'subscriptions', component: MainSubscriptionsViewComponent}
];//component: MapComponent

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
