import { Injectable } from '@angular/core';
import { IEmployee } from 'C:/Users/shiva/first/src/app/empinterface'
import { Http,Response } from '@angular/http';
import { Observable } from 'rxjs-compat/observable';
import 'rxjs-compat/add/operator/map';
import 'rxjs-compat/add/operator/catch';

@Injectable()
export class EmployeeServices{
	
constructor(private _http:Http){}

/*getEmployees():IEmployee {
	

return [
{name:'vivek',cmp:'tcs',gender:'male',date:'25/6/1991'},
{name:'vivek1',cmp:'tcs1',gender:'female',date:'25/7/1991'},
{name:'vivek2',cmp:'tcs2',gender:'female',date:'25/6/1991'},
{name:'vivek3',cmp:'tcs3',gender:'male',date:'25/6/1991'},
];
}*/

getEmployees():Observable<IEmployee[]>{
	return this._http.get("http://localhost:8081/restservice/rest/hello/get").map((res:Response)=><IEmployee[]>res.json()).catch((error)=>{
	console.log(error);
return throw error;

}
	);
}

}