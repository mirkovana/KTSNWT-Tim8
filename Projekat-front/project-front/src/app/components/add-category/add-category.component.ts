import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SubcategoryService } from 'src/app/services/subcategory.service';

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit {
  public name: string='';
  public breakpoint: number; // Breakpoint observer cod
  public addCusForm: FormGroup;
  wasFormChanged = false;

  constructor(private fb: FormBuilder,
    public dialog: MatDialog, private categoryService: SubcategoryService) { }

  ngOnInit(): void {
    this.addCusForm = this.fb.group({
      
      name: [this.name, [Validators.required]]
      
      
    });
    this.breakpoint = window.innerWidth <= 600 ? 1 : 2; // Breakpoint observer code
  }
  public onAddCus(): void {
    // this.markAsDirty(this.addCusForm);
    this.categoryService.addNewCategory(this.addCusForm.value);
    this.dialog.closeAll();
    //location.reload();
    //console.log(JSON.stringify(this.addCusForm.value) + "JEEEEEEEEEEEEEEEEEEEEEEEEEEEJJJJJJJJJJ");
  }

  openDialog(): void {
    this.dialog.closeAll();
  }

  public onResize(event: any): void {
    this.breakpoint = event.target.innerWidth <= 600 ? 1 : 2;
  }

  saveChangesEnabled() {
    return this.addCusForm.value.name.length > 0 ;
  }
 

  formChanged() {
    this.wasFormChanged = true;
  }
}
