import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';

import {ThemePalette} from '@angular/material/core';
import { mergeMap } from 'rxjs/operators';
import { OfferInfoService } from 'src/app/services/offer-info.service';
import { FormGroup, FormControl, FormArray, Validators, NgForm } from '@angular/forms';
import { ɵINTERNAL_BROWSER_DYNAMIC_PLATFORM_PROVIDERS } from '@angular/platform-browser-dynamic';
import { forkJoin } from 'rxjs';
import { FilterParameters } from 'src/app/models/Filter';
import { PaginatorPageable } from 'src/app/models/PaginatorPageable';
import { SubcategoryService } from 'src/app/services/subcategory.service';


@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {

  categories;
  subcategories;
  @ViewChild('f', { static: false }) filterForm: NgForm;
  dataIsAvailable = false;

  name="";
  location="";
  moreOpened = false;

  id: number;
  editMode = false;
  
  @Output() filterClick = new EventEmitter<FilterParameters>();
  @Output() filterCleared = new EventEmitter();

  allCompleteArray: boolean[] = [];

  constructor(private subcategoryService: SubcategoryService) { }

  ngOnInit(): void {

    forkJoin([
      this.subcategoryService.getAllCategories(), //observable 1
      this.subcategoryService.getAllSubcategories() //observable 2
    ]).subscribe(([categories, subcategories]) => {
      console.log(categories);
      console.log(subcategories)
        this.categories = categories;
        this.subcategories = subcategories;
        for (let c of this.categories){
          this.allCompleteArray.push(false);
          c.subcats = [];
          c.completed = false;
          c.allComplete = false;
        }
        for (let sub of this.subcategories){
          for (let cat of this.categories){
            if (sub.catID == cat.id){
              sub.completed = false;
              cat.subcats.push(sub);
            }
          }
        }
        this.dataIsAvailable = true;
    });

  }

  updateAllComplete1(i: number) {
    this.allCompleteArray[i] =
      this.categories[i].subcats != null &&
      this.categories[i].subcats.every(t => t.completed);
  }

  someComplete1(i: number): boolean {
    if (this.categories[i].subcats == null) {
      return false;
    }
    return (
      this.categories[i].subcats.filter(t => t.completed).length > 0 &&
      !this.allCompleteArray[i]
    );
  }

  setAll1(completed: boolean, i: number) {
    this.allCompleteArray[i] = completed;
    if (this.categories[i].subcats == null) {
      return;
    }
    this.categories[i].subcats.forEach(t => (t.completed = completed));
  }

  clearFilter(){
    this.filterForm.reset();
    this.filterCleared.emit();
  }

  onSubmit(form: NgForm) {
    console.log(form);
    let array2 = new Array();
    for (let category of this.categories) {
      for (let subcat of category.subcats) {console.log(subcat.completed);
      if (subcat.completed) {
        array2.push(subcat.id)
      }}
    }
    let text = form.form.value.text ? form.form.value.text : "";
    let location = form.form.value.location ? form.form.value.location : "";
    console.log(form.form.value.text + " a lokacija " + form.form.value.location)
    //this.filterClick.emit({"name": text, "place": location, "subcats":array2, "page": 0, "size": 5})
    this.filterClick.emit({"name": text, "place": location, "subcats":array2})  
  }

}
