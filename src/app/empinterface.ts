
export interface IEmployee{
name:string;	
cmp:string;
gender:string;
date:string;
department?:string //optional property
}
class Employee implements IEmployee
{
constructor(public name:string,public cmp:string,public gender:string,public date:string){}

computemonthlysalay(annualsal:number):number{
	return annualsal/12;

};
//ngOnInit,ngOnChanges,ngOnDestroy
}