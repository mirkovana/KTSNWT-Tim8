import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service'; 

@Component({
  selector: 'app-comment-edit',
  templateUrl: './comment-edit.component.html',
  styleUrls: ['./comment-edit.component.scss']
})
export class CommentEditComponent implements OnInit {

  username = localStorage.getItem('username');

  @Input() comment: Comment = new Comment(0, "", null, "", "", false, false, false, null);
  @Input() offerId;
  @Output() done = new EventEmitter<string>();
  

  @Output() newComment = new EventEmitter();
  @Output() commentEdited = new EventEmitter<Comment>();
  
  text = "neki tekst komentara";
  commentText = new FormControl();
  imageURL: string;
  uploadForm: FormGroup;

  ngOnInit(): void {
    console.log(this.offerId + " offerrr")
    console.log(this.comment)
    setTimeout(() => {
      //this.comment = new Comment(1, "tekst", null, "", new Date(), true, true, true, null);
      if (this.comment != null){ 
        if (this.comment.editing)
          this.uploadForm.patchValue({text: this.comment.text});
          if (this.comment.slika){
            this.uploadForm.patchValue({file: this.b64toBlob(this.comment.slika)})
          }
      }
      //console.log(this.uploadForm);
    });
  }


  constructor(public fb: FormBuilder, private commentService: CommentService, 
    private sanitizer: DomSanitizer) {
    // Reactive Form
    this.uploadForm = this.fb.group({
      file: [null],
      text: new FormControl(null, Validators.required)
    })
    console.log(this.uploadForm);
  }


  cancelEdit(){
    this.commentEdited.emit(this.comment)
  }

  // Image Preview
  showPreview(event) {
    //this.imageURL = ""
    const image = (event.target as HTMLInputElement).files[0];
    this.uploadForm.patchValue({
      file: image
    });
    this.uploadForm.get('file').updateValueAndValidity()

    // File Preview
    const reader = new FileReader();
    reader.onload = () => {
      this.imageURL = reader.result as string;
    }
    reader.readAsDataURL(image)
  }

  b64toBlob(dataURI) {

    var byteString = atob(dataURI.split(',')[1]);
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);

    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ab], { type: 'image/jpeg' });
}


  removeImage(){
    this.imageURL = "";
    this.uploadForm.patchValue({file: null});
    this.comment.slika = null;
  }

  // Submit Form - create or update
  submit() { 
    console.log(this.uploadForm.valid + " is valid?")

    let file = new Blob();
    if (this.uploadForm.value.file == null){
      
      if (this.comment.slika){
        file = this.b64toBlob(this.comment.slika);
      }
    }
    else {
      file = this.uploadForm.value.file;
      }
    // ako nema slike saljem prazan blob
    
    let text = this.uploadForm.value.text;
    console.log(text)

    if (this.comment.editing){
      
      this.comment.text = this.uploadForm.value.text;
    
      this.commentService.updateComment(this.comment.id, text, file).subscribe(data => {
        this.comment.imageBase64 = data['imageBase64'];
        if (this.comment.imageBase64 != null){
          this.comment.slika = 'data:image/jpg;base64,' + (this.sanitizer.bypassSecurityTrustResourceUrl(data['imageBase64']) as any).changingThisBreaksApplicationSecurity;
        }
        this.done.emit("Comment edited.");
        this.commentEdited.emit(this.comment)
      },  error => this.done.emit("Sorry! We couldn't update your comment, try again later."))
    }
    else {
      console.log(this.offerId + " to je offerid")
      this.commentService.createComment(this.offerId, text, file).subscribe(data => {
          this.newComment.emit();  
          this.comment = new Comment(0, "", null, "", "", false, false, false, null);
          this.imageURL = "";
          this.uploadForm.reset();
        this.done.emit("Comment created.");
      }, error => this.done.emit("Sorry! There was a problem with creating your comment, try again later."))  
    }
   
  }


}
