import { IfStmt } from '@angular/compiler';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { AnonymousSubject } from 'rxjs/internal/Subject';
import { OfferImageService } from 'src/app/services/offer-image.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {

  toastColor: string;
  subSuccess: string;
  cond: boolean = false;
  offerID: number;
  sub: any;
  imageURL: string;
  uploadForm: FormGroup;
  // uploadForm:string;
  error: string;
  error1: string;
  descPattern: string = "^[a-z0-9A-Z ]{1,20}$";

  constructor(
    public fb: FormBuilder,
    private offerImageService: OfferImageService,
    private router: Router,
    private route: ActivatedRoute,
    private cd: ChangeDetectorRef
  ) {
    // Reactive Form
    this.uploadForm = this.fb.group({
      avatar: [null],
      description: ['']
    })
  }

  ngOnInit(): void {
    this.sub = this.route
      .queryParams
      .subscribe(params => {
        // Defaults to 0 if no query param provided.
        this.offerID = +params['offerID'] || 0;

        console.log('Query param page: ', this.offerID);
      });
  }


  // Image Preview
  showPreview(event) {
    const file = (event.target as HTMLInputElement).files[0];
    this.uploadForm.patchValue({
      avatar: file
    });
    this.uploadForm.get('avatar').updateValueAndValidity();

    // File Preview
    const reader = new FileReader();
    reader.onload = () => {
      this.imageURL = reader.result as string;
    }
    reader.readAsDataURL(file)
  }

  // Submit Form
  submit() {
    let uslov: boolean = true;
    this.cond = false;
    let url: string = this.router.url;
    // let offerID: number;

    console.log(this.offerID)

    console.log(this.uploadForm.value);
    let validator = Validators.pattern("[A-Za-z0-9,.]*");
    if (this.uploadForm.get('description').value == "") {
      uslov = false;
      this.cond = true;
      this.subSuccess = ("Description is required.");
      this.toastColor = "red-snackbar";
      this.cd.detectChanges();
    }
    if (this.uploadForm.get('avatar').value == null) {
      uslov = false;
      this.cond = true;
      this.subSuccess = ("Image is required.");
      this.toastColor = "red-snackbar";
      this.cd.detectChanges();
    }
    if (uslov == false) {
      return;
    }
    else {
      console.log(typeof (this.uploadForm.get('avatar').value));
      const formData = new FormData();
      formData.append('image', this.uploadForm.get('avatar').value);
      this.offerImageService.uploadImage(this.offerID, this.uploadForm.get('avatar').value, this.uploadForm.get('description').value).subscribe(data => {
        this.cond = true;
        this.subSuccess = ("Image Successfully added to offer");
        this.toastColor = "green-snackbar";
        this.cd.detectChanges();
      }, 
      (error) => {                              //Error callback
        console.error('error caught in component')
        this.subSuccess = ("Error");
        this.toastColor = "red-snackbar";
        this.cond = true;
        this.cd.detectChanges();
      });
    }

    // if(!/[A-Z]*/.test(this.uploadForm.get('description').value)){
    //   this.error= "";
    // }else{
    //   console.log("USAO")
    //   this.error = "NEISPRAVAN UNOS";
    // }

  }
}
