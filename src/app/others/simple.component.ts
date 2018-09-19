import { Component,Input,OnChanges,SimpleChanges } from '@angular/core';

@Component({
	
selector:'simple',
template:`You typed:{{simpleinput}}`
})

export class Simplecomponent implements OnChanges
{
	@Input()
simpleinput:string;

ngOnChanges(changes:SimpleChanges){
	
for(let propertyName in changes)
{
	let change=changes[propertyName];
	let current=JSON.stringify(change.currentValue);
let previous=JSON.stringify(change.previousValue);
console.log(propertyName+'"curreny'+current+'previous'+previous);


}


}

}