import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { AuthenticationService } from '../services/authentication.service';

@Injectable({
	providedIn: 'root'
})
export class RoleGuard implements CanActivate {

	constructor(
		public auth: AuthenticationService,
		public router: Router
	) { }

	canActivate(route: ActivatedRouteSnapshot): boolean {
		const expectedRoles: string = route.data.expectedRoles;
		const token = localStorage.getItem('token');
		const jwt: JwtHelperService = new JwtHelperService();

		//ako token ne postoji idi na stranicu za logovanje, treba da ostane jer se radi samo o addOffer stranici
		if (!token) {
			this.router.navigate(['/login']);
			return false;
		}

		const info = jwt.decodeToken(token);
		const roles: string[] = expectedRoles.split('|', 2);

		if (roles.indexOf(info.role[0].authority) === -1) {
			this.router.navigate(['/home']);
			return false;
		}
		return true;
	}
}
