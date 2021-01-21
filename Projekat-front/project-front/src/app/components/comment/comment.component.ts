import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service';
import { DialogComponent } from '../dialog/dialog.component';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {


  @Input() comment: Comment = null;
  @Output() done = new EventEmitter<string>();

  //@Input() canEdit = false;
  editing = false;
  @Output() clickedEdit = new EventEmitter();
  @Output() commentDeleted = new EventEmitter();

  constructor(private commentService: CommentService, public dialog: MatDialog) { }

  
  ngOnInit(): void {

  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogComponent);
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
      if (result){
        this.deleteComment();
      }
    });
  }

  deleteComment(){
    
      this.commentService.deleteComment(this.comment.id).subscribe(() => {
        this.commentDeleted.emit();
        this.done.emit("Comment deleted.")
      })

  
  }

  onEdit(){
    this.clickedEdit.emit(this.comment.id);
  }

}
