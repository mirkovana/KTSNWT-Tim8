import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { CommentService } from 'src/app/services/comment.service';

import { CommentComponent } from './comment.component';

import { of } from 'rxjs';
import { Dialog } from 'primeng/dialog';
import { MatDialog } from '@angular/material/dialog';
import { DebugElement, EventEmitter } from '@angular/core';
import { Comment } from 'src/app/models/Comment';
import { By } from '@angular/platform-browser';
import { delay } from 'rxjs/operators';

describe('CommentComponent', () => {
  let component: CommentComponent;
  let fixture: ComponentFixture<CommentComponent>;
  let commentService: any;
  let dialog: any;


  beforeEach(async () => {

    let commentServiceMock = {
      deleteComment: jasmine.createSpy("deleteComment").and.returnValue(of(''))
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
    component.clickedEdit = new EventEmitter();
    component.done = new EventEmitter<string>();
    component.commentDeleted = new EventEmitter();
    spyOn(component.done, 'emit');
    spyOn(component.commentDeleted, 'emit');
    spyOn(component.clickedEdit, 'emit');

  });

  it('should create editable comment', () => {
    component.comment = new Comment(1, "Comment text", null, null,
    "user@gmail.com", new Date(), true, false, null); // user can edit
    fixture.detectChanges();
    expect(component).toBeTruthy();
    fixture.whenStable().then(() => { 
      let elements: DebugElement[] = 
      fixture.debugElement.queryAll(By.css('div.actions'));
      // making sure actions of deleting and editing are displayed
      expect(elements.length).toBe(1);    // ovde vrati 0 umjesto 1 :(
    })

  });

  it('should create non-editable comment', () => {
    component.comment = new Comment(1, "Comment text", null, null,
    "kor1@nesto.com", new Date(), false, false, null); // user cannot edit
    fixture.detectChanges();
    expect(component).toBeTruthy();
    fixture.whenStable().then(() => { 
      let elements: DebugElement[] = 
      fixture.debugElement.queryAll(By.css('div.actions'));
      expect(elements.length).toBe(0);
    })
  });

  it('should emit edit event', () => {
    component.comment = new Comment(1, "Comment text", null, null,
    "user@gmail.com", new Date(), true, false, null); // user can edit
    fixture.detectChanges();
    component.onEdit();
    expect(component.clickedEdit.emit).toHaveBeenCalledWith(component.comment.id);
  })

  it('should call deleteComment', fakeAsync(() => {
    component.comment = new Comment(1, "Comment text", null, null,
    "user@gmail.com", new Date(), true, false, null); // user can edit
    fixture.detectChanges();
    component.deleteComment();
    expect(commentService.deleteComment).toHaveBeenCalledWith(component.comment.id);
    tick();
    expect(component.commentDeleted.emit).toHaveBeenCalled();
    expect(component.done.emit).toHaveBeenCalledWith('Comment deleted.');
  }))


  // dialog tests

});
