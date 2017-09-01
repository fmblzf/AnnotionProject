package test.andann.module;

import test.andann.base.DoubleClick;
import test.andann.base.LongOnClickListener;
import test.andann.base.OnClickListener;

public class ViewModel {
	
	public String name = "";
	
	private OnClickListener listener;
	private LongOnClickListener longListener;
	private DoubleClick doubleClick;
	
	public void click(){
		if(listener == null)
			return;
		listener.click();
	}
	
	public void longClick(){
		if(longListener == null)
			return;
		longListener.longClick();
	}
	
	public void doubleClick(){
		if(doubleClick == null) 
			return ;
		doubleClick.doubleClick(this);
	}
	
	public void setLongOnClickListener(LongOnClickListener longlistener){
		this.longListener = longlistener;
	}
	
	public void setOnClickListener(OnClickListener listener){
		this.listener = listener;
	}
	
	public void setDoubleClick(DoubleClick dou){
		this.doubleClick = dou;
	}
}
