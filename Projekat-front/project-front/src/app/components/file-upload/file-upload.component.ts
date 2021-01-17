import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { OfferImageService } from 'src/app/services/offer-image.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent implements OnInit {

  imageURL: string;
  uploadForm: FormGroup;
  // uploadForm:string;
  error:string;
  error1:string;
  descPattern:string =  "^[a-z0-9A-Z]{1,20}$"; 

  constructor(public fb: FormBuilder, private offerImageService: OfferImageService) {
    // Reactive Form
    this.uploadForm = this.fb.group({
      avatar: [null],
      description: ['']
    })
  }

  ngOnInit(): void { }


  // Image Preview
  showPreview(event) {
    const file = (event.target as HTMLInputElement).files[0];
    this.uploadForm.patchValue({
      avatar: file
    });
    this.uploadForm.get('avatar').updateValueAndValidity()

    // File Preview
    const reader = new FileReader();
    reader.onload = () => {
      this.imageURL = reader.result as string;
    }
    reader.readAsDataURL(file)
  }

  // Submit Form
  submit() {
    let uslov:boolean = true;
    this.error = "";
    this.error1 = "";
    // this.uploadForm.patchValue({
    //   description: "IDEMO"
    // });
    console.log(this.uploadForm.value)
    let validator = Validators.pattern("[A-Za-z0-9,.]*")
    if(this.uploadForm.get('description').value == ""){
      this.error = "Description is required.";
      uslov = false;
    }
    if(this.uploadForm.get('avatar').value == null){
      this.error1 = "Image is required.";
      uslov = false;
    }
    if(uslov == false){
      return;
    }
    else{
      console.log(typeof(this.uploadForm.get('avatar').value));
      const formData = new FormData();
      formData.append('image', this.uploadForm.get('avatar').value);
      console.log("IDEMO")
      this.offerImageService.uploadImage(1, this.uploadForm.get('avatar').value, this.uploadForm.get('description').value)
    }
    
    // if(!/[A-Z]*/.test(this.uploadForm.get('description').value)){
    //   this.error= "";
    // }else{
    //   console.log("USAO")
    //   this.error = "NEISPRAVAN UNOS";
    // }
    
  }
}
