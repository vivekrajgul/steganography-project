import { Pipe,PipeTransform } from '@angular/core';
@Pipe({
	
name:'employeeTitle'

})
export class EmployeeTitlepipe implements PipeTransform{
	
transform(value:String,gender:string):string{
	
if(gender.toLowerCase() == 'male')
{return "Mr."+ value;
}else return "Miss."+value;
}


}