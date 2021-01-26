import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Params } from '@angular/router';
import { PageGen } from 'src/app/models/Offer';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss']
})
export class CommentsComponent implements OnInit {

  username = localStorage.getItem("username"); 

  offerId = 0;   

  content: PageGen = null;   

  paginatorDetails: PaginatorPageable = new PaginatorPageable(0, 0, 5, 0);    // inital setup of page

  dataReady = false;

  admin = false;


  constructor(private service: CommentService, private sanitizer: DomSanitizer,
    private route: ActivatedRoute, private _snackBar: MatSnackBar) { }


  openSnackBar(message) {
    this._snackBar.open(message, "Close", {
      duration: 2000,
      panelClass: ['blue-snackbar']
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.offerId = +params['id'];
      console.log("offer id iz koments " + this.offerId)
      this.username = localStorage.getItem("username");
      this.admin = this.username === "admin@nesto.com";
    });
    this.service.getCommentsFromOffer(this.offerId, this.paginatorDetails.pageSize, 0).subscribe(data => {
      this.setupComments(data)
      this.dataReady = true;
    });
  }

  setupComments(content: PageGen) {
    this.content = content;
    let i = 0;
    for (let c of this.content.content) {
      if (c.username == this.username) {
        c.canEdit = true;
      }
      else {
        c.canEdit = false;
      }
      //c.canEdit = true;
      if (c.imageBase64 != null) {
        c.slika = 'data:image/jpg;base64,' + (this.sanitizer.bypassSecurityTrustResourceUrl(c.imageBase64) as any).changingThisBreaksApplicationSecurity;
      }
      c.date = new Date(c.date)
      c.editing = false;
    }
    this.paginatorDetails.length = content.totalElements;
    this.paginatorDetails.pageSize = content['size']; //size dodati u page
    //data.totalPages;
    console.log(this.content);
  }


  onCommentDeleted() {

    this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data => {
      this.setupComments(data);
    })

  }

  onCommentEdited(comm) {
    console.log(comm);
    for (let c of this.content.content) {
      if (comm.id == c.id) {
        c = comm;
        c.editing = false;
      }
    }
  }


  onCommentCreated(aj) {
    this.paginatorDetails.pageIndex = 0;
    this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data => {
      this.setupComments(data)
    })
  }

  // ovo koristim u comment
  onClickedEdit(event) {
    console.log(event);
    // nadji taj komentar
    //mozda neku mapu
    for (let c of this.content.content) {
      if (c.id == event) {
        c.editing = true;
      }
    }

  }

  onNewComment() {
    this.paginatorDetails.pageIndex = 0;
    this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data => {
      this.setupComments(data)
    })
  }

  onClick(event) {
    console.log("kliknuto na paginator")
    this.paginatorDetails = event;
    this.service.getCommentsPage(event, this.offerId).subscribe(data => {
      this.setupComments(data)
    })

  }


}
