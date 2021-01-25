import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By, DomSanitizer } from '@angular/platform-browser';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service';
import { CommentEditComponent } from './comment-edit.component';
import { of } from 'rxjs';
import { DebugElement, EventEmitter } from '@angular/core';

describe('CommentEditComponent', () => {
  let component: CommentEditComponent;
  let fixture: ComponentFixture<CommentEditComponent>;
  let commentService: any;
  let fb: any;
  let sanitizer: any;

  beforeEach(async () => {

    let commentServiceMock = {
      createComment: jasmine.createSpy("createComment").and.returnValue(of("")),
      updateComment: jasmine.createSpy("updateComment").and.returnValue(of(""))
    }

    let formBuilderMock = {
      group: jasmine.createSpy("group").and.returnValue("")
    }

    let domSanitizerMock = {}

    await TestBed.configureTestingModule({
      imports: [FormsModule, ReactiveFormsModule],
      declarations: [ CommentEditComponent ],
      providers: [{provide: CommentService, useValue: commentServiceMock},
        //{provide: FormBuilder, useValue: FormBuilder},
      {provide: DomSanitizer, useValue: domSanitizerMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentEditComponent);
    component = fixture.componentInstance;
    commentService = TestBed.inject(CommentService);
    //fb = TestBed.inject(FormBuilder);
    sanitizer = TestBed.inject(DomSanitizer);
    component.done = new EventEmitter<string>();
    component.commentEdited = new EventEmitter<Comment>();
    component.newComment = new EventEmitter();
    spyOn(component.commentEdited, 'emit');
    spyOn(component.done, 'emit');
    spyOn(component.newComment, 'emit');
    spyOn(component.uploadForm, 'reset');
  });

  it('should create new comment component', fakeAsync(() => {
    component.comment = new Comment(0, "", null, "", "", false, false, false, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();
    expect(component.uploadForm.value.text).toBe(null); 
    expect(component.uploadForm.value.file).toBe(null); 
    expect(component).toBeTruthy();
  }));


  it('should create editing comment component', fakeAsync(() => {
    component.comment = new Comment(1, "Comment", null, "", "username@gmail.com", new Date() , true, true, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();   // jer imam set timeout u ngoninit
    //fixture.detectChanges();
    console.log(component.uploadForm.value);
    expect(component.uploadForm.value.text).toBe("Comment"); 
    expect(component.uploadForm.value.file).toBe(null); 
    //let 
    expect(component).toBeTruthy();
  }));

  it('should submit new comment', fakeAsync(() => {
    component.comment = new Comment(1, "", null, "", "username@gmail.com", new Date(), true, false, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();   // jer imam set timeout u ngoninit
    component.uploadForm.value.text = "";
    //klikni na dugme?
    component.submit();
    tick();
    expect(commentService.createComment).toHaveBeenCalled();    // moze i with
    expect(component.newComment.emit).toHaveBeenCalled();
    expect(component.done.emit).toHaveBeenCalledWith("Comment created.");
    expect(component.uploadForm.reset).toHaveBeenCalled();
    
  }))

  it('should edit comment', fakeAsync(() => {
    component.comment = new Comment(1, "Comment", null, "", "username@gmail.com", new Date() , true, true, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();   // jer imam set timeout u ngoninit
    component.uploadForm.value.text = "";
    //klikni na dugme?
    component.submit();
    tick();
    expect(commentService.updateComment).toHaveBeenCalled();
    expect(component.commentEdited.emit).toHaveBeenCalled();
    expect(component.done.emit).toHaveBeenCalledWith("Comment edited.");
    
  }))

});
