import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Page } from 'src/app/models/Post';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { PostService } from 'src/app/services/post.service';
import { ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  @Input() page: Page;
  @Input() paginatorDetails: PaginatorPageable;
  offerId = 0;


  //*********************//
  @Output() pageChanged = new EventEmitter<PaginatorPageable>();

  constructor(private postService:PostService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params
    .subscribe(
      (params: Params) => {
        this.offerId = +params['id'];
        this.postService.getPostsPage(this.offerId, this.paginatorDetails).subscribe(data => {
          this.page = data;
          this.paginatorDetails.length = data.totalElements;
        })
      }
    );
  }

  onClick(event){
    //console.log(event)
    this.pageChanged.emit(event);

    //this.offersService.emitChildEvent(event);
  }

}
