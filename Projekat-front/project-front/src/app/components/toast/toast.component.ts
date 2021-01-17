import { Component, Input, OnChanges, OnInit, SimpleChange } from '@angular/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';


@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss'],
})
export class ToastComponent implements OnInit {

  @Input() succ: string;
  @Input() color: string;
  @Input() cond: boolean;
  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChange) {
    if (this.cond) {
      this.openSnackBar(this.succ, 'ok', this.color);
    }
  }

  constructor(private _snackBar: MatSnackBar) { }

  openSnackBar(message: string, action: string, className: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
      verticalPosition: 'bottom',
      horizontalPosition: 'right',
      panelClass: [className]
    });
    // this.succ = null;
  }
}


