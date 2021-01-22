import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { Category } from 'src/app/models/Category';
import { SubcategoryService } from 'src/app/services/subcategory.service';
@Component({
  selector: 'app-add-subcategory',
  templateUrl: './add-subcategory.component.html',
  styleUrls: ['./add-subcategory.component.scss']
})
export class AddSubcategoryComponent implements OnInit {
  subcategoryControl = new FormControl('', Validators.required);
  public name: string='';
  public breakpoint: number; // Breakpoint observer cod
  public addCusForm: FormGroup;
  wasFormChanged = false;
  selectedItem:Category;
  category$:Observable<Category[]>;
  category1;


  constructor(private fb: FormBuilder,
    public dialog: MatDialog, private categoryService: SubcategoryService) { }

  ngOnInit(): void {
    this.addCusForm = this.fb.group({
      
      name: [this.name, [Validators.required]]
      
      
    });
    this.breakpoint = window.innerWidth <= 600 ? 1 : 2; // Breakpoint observer code


    this.categoryService.getAllCategories().subscribe((category1)=>{
      
      this.category1 = category1;
    });
  }
  public onAddCus(): void {
    // this.markAsDirty(this.addCusForm);
    this.categoryService.addNewSubcategory(this.addCusForm.value, this.selectedItem.id);
    this.dialog.closeAll();
    //location.reload();
    
  }

  openDialog(): void {
    this.dialog.closeAll();
  }

  public onResize(event: any): void {
    this.breakpoint = event.target.innerWidth <= 600 ? 1 : 2;
  }

  saveChangesEnabled() {
    return this.addCusForm.value.name.length > 0 && this.selectedItem;
  }
 

  formChanged() {
    this.wasFormChanged = true;
  }
}
