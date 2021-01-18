import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent implements OnInit {


  @Input() comment: Comment = null;
  
  //@Input() canEdit = false;
  editing = false;
  @Output() clickedEdit = new EventEmitter();
  @Output() commentDeleted = new EventEmitter();

  constructor(private commentService: CommentService) { }

  
  ngOnInit(): void {

  }

  onDelete(){
    
      this.commentService.deleteComment(this.comment.id).subscribe(() => {
        this.commentDeleted.emit();
      })

  
  }

  onEdit(){
    this.clickedEdit.emit(this.comment.id);
  }

}
