import { Component ,OnInit} from '@angular/core';
import { IEmployee } from 'C:/Users/shiva/first/src/app/empinterface';
import { EmployeeServices } from 'C:/Users/shiva/first/src/app/services/employee.services';

@Component({
	
selector: 'emplist12',
templateUrl:'./emplist.html',
providers:[EmployeeServices]
})

export class emplist implements OnInit



{
selectedemployees:string='all';
statusmessage:string;
employees:IEmployee[];
constructor(private empservice:EmployeeServices)
{
}
ngOnInit()
{
this.empservice.getEmployees().subscribe((emps)=>this.employees=emps,error=>{
	
	this.statusmessage='Problem with the server';
});
}

	setradiobuttoonvalue(selectedvalue:string):void{
this.selectedemployees=selectedvalue;

	}

getemp():void{	
this.employees=[
{name:'vivek',cmp:'tcs',gender:'male',date:'25/6/1991'},
{name:'vivek1',cmp:'tcs1',gender:'female',date:'25/7/1991'},
{name:'vivek2',cmp:'tcs2',gender:'female',date:'25/8/1991'},
{name:'vivek3',cmp:'tcs3',gender:'male',date:'25/10/1991'},
{name:'vivek3u',cmp:'tcs3u',gender:'male',date:'25/4/1991'},
];
}
/*trackbycode(index : number,employee : IEmployee):string {
	return employees.code
}*/

getall():number{
	return this.employees.length;
}

getmale():number{
	return this.employees.filter((e)=>e.gender==='male').length;

}
getfemale():number{
	return this.employees.filter((e)=>e.gender==='female').length;

}



}