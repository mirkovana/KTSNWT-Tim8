import { Component,Input, OnInit } from '@angular/core';
import { Post } from 'src/app/models/Post';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.scss']
})
export class PostItemComponent implements OnInit {
  @Input() post;
  admin:boolean=false;

  editedTitle:string;

  loggedIn = localStorage.getItem('username');
  broj:number;


  constructor(private postService:PostService) { }

  ngOnInit(): void {

    if(this.loggedIn){
      
      if(this.loggedIn==="admin@nesto.com"){this.broj=1;this.admin=true;} //kad je ulogovan admin
      else{this.broj=2;} //kad je ulogovan korisnik koji nije admin
    } 
  }

  saveChangesEnabled() {
    return this.post.title.length > 0 && this.post.content.length > 0;
  }

  saveChanges(post1: Post) {
    this.postService.updatePost(post1);
    location.reload();
  }

  deletePost(post1: Post){
    this.postService.deletePost(post1.id);
    
    location.reload();
  }
}
