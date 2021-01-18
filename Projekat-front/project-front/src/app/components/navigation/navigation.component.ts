import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  loggedIn = localStorage.getItem('username');
  broj:number;
  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    //u htmlu sa !loggedIn je sve ono sto se prikazuje kad korisnik nije ulogovan uopste  
  if(this.loggedIn){
    console.log("ulogovan je neko");
    if(this.loggedIn==="admin@nesto.com"){this.broj=1;} //kad je ulogovan admin
    else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
  } 
  }

  logout():void{
    this.authenticationService.logout();
    window.location.reload();
  }

}
