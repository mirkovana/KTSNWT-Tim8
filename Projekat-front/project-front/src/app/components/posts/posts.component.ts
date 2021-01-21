import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/models/Post';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  @Input() page: Page;
  @Input() paginatorDetails: PaginatorPageable;

  //*********************//
  @Output() pageChanged = new EventEmitter<PaginatorPageable>();

  constructor(private postService:PostService) { }

  ngOnInit(): void {
    console.log("NAAAAAAAAAAAAAAAAAAAS" + JSON.stringify(this.page));
  }

  onClick(event){
    //console.log(event)
    this.pageChanged.emit(event);

    //this.offersService.emitChildEvent(event);
  }

}
