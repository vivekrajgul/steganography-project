import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
 /*template:`<input type="text" [(ngModel)]='usertext'/>
<br/><br/>
<simple [simpleinput]='usertext'></simple>
 `*/
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title : string = 'my first ';

usertext:string='you';
}
