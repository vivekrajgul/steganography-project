import { Component } from '@angular/core';


@Component
({
	selector:'emp',
	templateUrl:'./emp_view.html'

})

export class employee
{
	name: String = 'vivek';
	cmp: String = 'tcs';
	sal : number = 30000;
	display : boolean = false;
	btext : string = 'show details';

	showdetails() : void {

this.display = !this.display ;
	if(this.display)
this.btext='showdetails';
else
this.btext='hide details';
	}



}