import { Component,Input,Output, EventEmitter } from '@angular/core';

@Component({	
selector:'empcount',
templateUrl:'./empcount.html'
})
export class empcount{

radiobuttonvalue:string='all';

@Output()
radiobuttonvalueinjected:EventEmitter<string>=new EventEmitter<string>();


	@Input()
	all:number;
    @Input()
    male:number;
    @Input()
    female:number;

injectradiovalue(){
	this.radiobuttonvalueinjected.emit(this.radiobuttonvalue);
}
}