package test.andann;

import test.andann.ann.Method;
import test.andann.ann.View;
import test.andann.ann.Window;
import test.andann.base.BaseContext;
import test.andann.module.ViewModel;
import test.andann.utils.InjectAnn;

@Window(layoutId=1024)
public class Activity extends BaseContext {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		InjectAnn.inject(this);
	}
	
	@View(viewId=123)
	public ViewModel view;
	
	@Method(viewId=123,eventName="click")
	public void curClick(){
		System.out.println("curClick ... ");
	}
	@Method(viewId=123,eventName="longclick")
	public void curLongClick(){
		System.out.println("curLongClick ... ");
	}
	@Method(viewId=123,eventName="doubleclick")
	public void curDoubleClick(ViewModel view){
		System.out.println("curDoubleClick ... "+view.name);
	}	

}
