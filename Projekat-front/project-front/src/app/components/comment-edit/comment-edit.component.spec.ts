import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By, DomSanitizer } from '@angular/platform-browser';
import { Comment } from 'src/app/models/Comment';
import { CommentService } from 'src/app/services/comment.service';
import { CommentEditComponent } from './comment-edit.component';
import { of } from 'rxjs';
import { DebugElement } from '@angular/core';

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
    
    
  });

  it('should create comment being edited', fakeAsync(() => {
    component.comment = new Comment(1, "asksadkkasd", null, "", new Date(), true, true, true, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();
    fixture.detectChanges();
    console.log("\nforma\n\n\n\n\n\n\n")
    console.log(component.uploadForm.value);
    // component.comment = new Comment(0, "", null, "", "", false, false, false, null);
    // component.offerId = "1";
    let element: DebugElement = fixture.debugElement.query(By.css('textarea'));
    console.log(element.nativeElement);
    console.log(element);
    
    expect(component).toBeTruthy();
  }));


  it('should create new comment', fakeAsync(() => {
    component.comment = new Comment(1, "asksadkkasd", null, "", new Date(), true, true, true, null);
    component.offerId = "1";
    fixture.detectChanges();
    tick();
    //fixture.detectChanges();
    console.log("\nforma\n\n\n\n\n\n\n")
    console.log(component.uploadForm.value);
    // component.comment = new Comment(0, "", null, "", "", false, false, false, null);
    // component.offerId = "1";
    let element: DebugElement = fixture.debugElement.query(By.css('textarea'));
    console.log(element.nativeElement);
    console.log(element);
    
    expect(component).toBeTruthy();
  }));
});
