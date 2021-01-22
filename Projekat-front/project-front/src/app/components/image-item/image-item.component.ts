import { Component, OnInit, Input} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-image-item',
  templateUrl: './image-item.component.html',
  styleUrls: ['./image-item.component.scss']
})
export class ImageItemComponent implements OnInit {
  @Input() post;
  putanja;
  imageSource;
  constructor(private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.putanja= JSON.stringify(this.post.imageBase64)
    this.imageSource= 'data:image/jpg;base64,' + (this.sanitizer.bypassSecurityTrustResourceUrl(this.post.imageBase64) as any).changingThisBreaksApplicationSecurity;
  }


}
