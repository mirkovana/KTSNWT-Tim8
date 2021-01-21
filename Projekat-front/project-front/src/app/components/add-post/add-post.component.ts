import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from 'src/app/services/post.service';
@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.scss']
})
export class AddPostComponent implements OnInit {
  public title: string = ``;
  public content: string = ``;
  public breakpoint: number; // Breakpoint observer cod
  public addCusForm: FormGroup;
  wasFormChanged = false;
  constructor(private fb: FormBuilder,
    public dialog: MatDialog, private postService: PostService) { }

  ngOnInit(): void {

    this.addCusForm = this.fb.group({
      
      title: [this.title, [Validators.required]],
      content: [this.content, [Validators.required]],
      
    });
    this.breakpoint = window.innerWidth <= 600 ? 1 : 2; // Breakpoint observer code
  }


  public onAddCus(): void {
    // this.markAsDirty(this.addCusForm);
    this.postService.addPost(this.addCusForm.value);
    this.dialog.closeAll();
    location.reload();
    //console.log(JSON.stringify(this.addCusForm.value) + "JEEEEEEEEEEEEEEEEEEEEEEEEEEEJJJJJJJJJJ");
  }
  saveChangesEnabled() {
    return this.addCusForm.value.title.length > 0 && this.addCusForm.value.content.length > 0 ;
  }
  openDialog(): void {
    this.dialog.closeAll();
  }

  public onResize(event: any): void {
    this.breakpoint = event.target.innerWidth <= 600 ? 1 : 2;
  }

 

  formChanged() {
    this.wasFormChanged = true;
  }

}
