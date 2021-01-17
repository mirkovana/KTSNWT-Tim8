import { Component, OnInit } from '@angular/core';
import { FormControl,FormBuilder, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import { ValiteEmailService } from '../../services/valite-email.service';
@Component({
  selector: 'validateEmail/',
  templateUrl: './validate-email.component.html',
  styleUrls: ['./validate-email.component.scss']
})
export class ValidateEmailComponent implements OnInit {
  activateForm: FormGroup;
  loading = false;
  submitted = false;
  constructor(
    private formBuilder: FormBuilder,
    private validateService:ValiteEmailService
  ) { }

  ngOnInit(): void {
    this.activateForm = this.formBuilder.group({
      token: ['', Validators.required]
  });
  }

  get f() { return this.activateForm.controls; }
  onSubmit() {
    this.submitted = true;
    if (this.activateForm.invalid) {
      return;
  }
  this.loading = true;
  this.validateService.validateEmail(this.activateForm.value.token);
}}
