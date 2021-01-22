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

  username=localStorage.getItem("username"); // ovo treba da se dobije kao input ili tako nesto

  //username="kor1@nesto.com"

  offerId = 2;   // dobijen iz urla, ili da primi kao input()

  content: PageGen = null;   // da stavim tamo da je content any?
  
  paginatorDetails: PaginatorPageable = new PaginatorPageable(0, 0, 5, 0);    // inital setup of page

  dataReady = false;
  //@ViewChild(MatPaginator) paginator: MatPaginator;

  // paginator ne radi
  dict = {}

  constructor(private service: CommentService, private sanitizer: DomSanitizer,
    private route: ActivatedRoute, private _snackBar: MatSnackBar) { }


    openSnackBar(message) {
      this._snackBar.open(message, "Close", {
        duration: 2000,
        panelClass: ['blue-snackbar']
      });
    }

  ngOnInit(): void {
      // aj nek je dobijem iz urla
      // mozda da se posalje iz parent komponente?
      this.route.params.subscribe((params: Params) => {
        this.offerId = +params['id'];
        this.username=localStorage.getItem("username");
       });
      this.service.getCommentsFromOffer(this.offerId, this.paginatorDetails.pageSize, 0).subscribe(data=> {
          this.setupComments(data)
          this.dataReady = true;
      });
      }

      setupComments(content: PageGen){
        this.content = content;
        let i=0;
        for (let c of this.content.content){
            this.dict[c.id] = c;
            if (c.username == this.username){
              c.canEdit = true;
            }
            else {
              c.canEdit = false;
            }
            //c.canEdit = true;
            if (c.imageBase64 != null){
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


    onCommentDeleted(){

    this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data =>{
      this.setupComments(data);    
      })
      
      }


      radiNesto(){
        console.log("o radi nesto")
      }


      onCommentEdited(comm){
        console.log(comm);
        for (let c of this.content.content){
          if (comm.id == c.id){
            c = comm;
            c.editing = false;
          }
        }
      }

      newComment(c){
        console.log(c);
      }

      onCommentCreated(aj){
        this.paginatorDetails.pageIndex = 0;
        this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data =>{
          this.setupComments(data)    
         })
      }

      onClickedEdit(event){
        console.log(event);
        // nadji taj komentar
        //mozda neku mapu
        for (let c of this.content.content){
          if (c.id==event){
            c.editing = true;
          }
        }

      }

      onNewComment(){
        this.paginatorDetails.pageIndex = 0;
        this.service.getCommentsPage(this.paginatorDetails, this.offerId).subscribe(data =>{
          this.setupComments(data)    
         })
      }

      onClick(event){
        console.log("kliknuto na paginator")
        this.paginatorDetails = event;
        this.service.getCommentsPage(event, this.offerId).subscribe(data =>{
          this.setupComments(data)
        })
        
      }


}
