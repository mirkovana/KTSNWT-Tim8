import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CommentService } from 'src/app/services/comment.service';

import { CommentComponent } from './comment.component';

import { of } from 'rxjs';
import { Dialog } from 'primeng/dialog';
import { MatDialog } from '@angular/material/dialog';
import { EventEmitter } from '@angular/core';
import { Comment } from 'src/app/models/Comment';

describe('CommentComponent', () => {
  let component: CommentComponent;
  let fixture: ComponentFixture<CommentComponent>;
  let commentService: any;
  let dialog: any;


  beforeEach(async () => {

    let commentServiceMock = {
      deleteComment: jasmine.createSpy("deleteComment")
      .and.returnValue(of())
    }

    let dialogMock = { open: jasmine.createSpy('open') }

    await TestBed.configureTestingModule({
      declarations: [ CommentComponent ],
      providers: [{provide: CommentService, useValue: commentServiceMock},
      {provide: MatDialog, useValue: dialogMock}]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentComponent);
    component = fixture.componentInstance;
    commentService = TestBed.inject(CommentService);
    dialog = TestBed.inject(MatDialog);
    //component.comment = new Comment(1, "Comment text", null, null,
    //"kor1@nesto.com", new Date(), true, false, null);
    //component.done = new EventEmitter<string>();
    //component.clickedEdit = new EventEmitter();
    //component.commentDeleted = new EventEmitter();
    
  });

  it('should create', () => {
    //const comp = new CommentComponent();
    //component.ngOnInit();
    //component.comment = new Comment(1, "Comment text", null, null,
    //"kor1@nesto.com", new Date(), true, false, null);
    //fixture.detectChanges();
    component.comment = new Comment(1, "Comment text", null, null,
    "kor1@nesto.com", new Date(), true, false, null);
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });
});
