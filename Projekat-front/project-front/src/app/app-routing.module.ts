import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { MapComponent } from './components/map/map.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {ValidateEmailComponent} from './components/validate-email/validate-email.component';
import {LoginComponent} from './components/login/login.component';

const routes: Routes = [ 
  { path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: MainOffersViewComponent},
  
{
  path: 'registration', component: RegistrationComponent
},
{path: 'validateEmail', component: ValidateEmailComponent},
{path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
