import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './components/map/map.component';
import { from } from 'rxjs';
import { MainOffersViewComponent } from './components/main-offers-view/main-offers-view.component';
import { OfferModalComponent } from './components/offer-modal/offer-modal.component';
import { NgbModule, NgbToastModule } from '@ng-bootstrap/ng-bootstrap';
import {AccordionModule} from 'primeng/accordion';     //accordion and accordion tab
import {MenuItem, SharedModule} from 'primeng/api'; 
import {DialogModule} from 'primeng/dialog';
import {ButtonModule} from 'primeng/button';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCarouselModule } from '@ngmodule/material-carousel';
import { MainSubscriptionsViewComponent } from './components/main-subscriptions-view/main-subscriptions-view.component';
import { SubscriptionTableComponent } from './components/subscription-table/subscription-table.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ToastComponent } from './components/toast/toast.component';
import { MatFormFieldModule  } from '@angular/material/form-field';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ValidateEmailComponent } from './components/validate-email/validate-email.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { EditOfferComponent } from './components/edit-offer/edit-offer.component';
import { MatIconModule } from '@angular/material/icon';

import { LoginComponent } from './components/login/login.component';
import { FilterComponent } from './components/filter/filter.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { CommonModule } from '@angular/common'; 
import { MatButtonModule } from '@angular/material/button';
import { OffersComponent } from './components/offers/offers.component';
import {MatDividerModule} from '@angular/material/divider';
import { OfferItemComponent } from './components/offer-item/offer-item.component';
import { OfferComponent } from './components/offer/offer.component';
import { CommentsComponent } from './components/comments/comments.component';
import { CommentComponent } from './components/comment/comment.component';
import { CommentEditComponent } from './components/comment-edit/comment-edit.component';
import {MatCardModule} from '@angular/material/card';
import { RatingComponent } from './components/rating/rating.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { AddOfferComponent } from './components/add-offer/add-offer.component';
import {MatSelectModule} from '@angular/material/select';
import { PostsComponent } from './components/posts/posts.component';
import { PostItemComponent } from './components/post-item/post-item.component';
import { AddPostComponent } from './components/add-post/add-post.component';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatDialogModule} from '@angular/material/dialog';
import { AddCategoryComponent } from './components/add-category/add-category.component';
import { AddSubcategoryComponent } from './components/add-subcategory/add-subcategory.component';
import { AddOfferImageComponent } from './components/add-offer-image/add-offer-image.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { OfferImagesComponent } from './components/offer-images/offer-images.component';
import { ImageItemComponent } from './components/image-item/image-item.component';
import { DeleteOfferDialogComponent } from './components/delete-offer-dialog/delete-offer-dialog.component';
import { DeletePostDialogComponent } from './components/delete-post-dialog/delete-post-dialog.component';


@NgModule({
  declarations: [
    AppComponent,
    MainOffersViewComponent,
    MapComponent,
    OfferModalComponent,
    MainSubscriptionsViewComponent,
    SubscriptionTableComponent,
    ToastComponent,
    RegistrationComponent,
    NavigationComponent,
    ValidateEmailComponent,
    FileUploadComponent,
    EditOfferComponent,
    LoginComponent,
    FilterComponent,
    OffersComponent,
    OfferItemComponent,
    OfferComponent,
    CommentsComponent,
    CommentComponent,
    CommentEditComponent,
    RatingComponent,
    AddOfferComponent,
    PostsComponent,
    PostItemComponent,
    AddPostComponent,
    AddCategoryComponent,
    AddSubcategoryComponent,
    AddOfferImageComponent,
    DialogComponent,
    OfferImagesComponent,
    ImageItemComponent,
    DeleteOfferDialogComponent,
    DeletePostDialogComponent


   

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    AccordionModule,
    DialogModule,
    ButtonModule,
    BrowserAnimationsModule,
    MatCarouselModule,
    MatTableModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    FormsModule, 
    MatCheckboxModule,
    CommonModule,
    MatButtonModule,
    MatDividerModule,
    MatCardModule,
    MatTooltipModule,
    MatSelectModule,
    MatGridListModule,
    MatDialogModule
    
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
