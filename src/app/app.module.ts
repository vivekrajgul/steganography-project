import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { emplist } from './employee/emplist';
import { EmployeeTitlepipe } from './employee/custompipe';
import { empcount } from './employee/empcount';
import { Simplecomponent} from './others/simple.component';
import { employee } from './employee/emp.component';

@NgModule({
  declarations: [
    AppComponent,employee,emplist,EmployeeTitlepipe,empcount,Simplecomponent
  ],
  imports: [
    BrowserModule,FormsModule,HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
