package test.andann.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import test.andann.ann.View;
import test.andann.ann.Window;
import test.andann.base.BaseContext;
import test.andann.base.DoubleClick;
import test.andann.base.LongOnClickListener;
import test.andann.base.OnClickListener;
import test.andann.module.ViewModel;

public class InjectAnn {

	private static Map<Integer,Map<Integer,ViewModel>> map = null;
	private static Map<Integer,ViewModel> subMap = null;
	private static BaseContext mContent;
	
	
	public static void inject(BaseContext content){
		mContent = content;
		init();
		Class<?> clasz = content.getClass();
		boolean isAnn = clasz.isAnnotationPresent(Window.class);
		if(!isAnn) return;
		Window window = clasz.getAnnotation(Window.class);
		int layoutId = window.layoutId();
		Map<Integer,ViewModel> submap = getMapById(layoutId);
		//初始化屬性
		initAttrs(submap,clasz);
		//初始化方法
		initMethods(submap,clasz);
	}
	
	private static void initMethods(Map<Integer,ViewModel> submap,Class<?> clasz) {
		Method[] methods = clasz.getMethods();
		if(methods == null || methods.length == 0) return;
		for(final Method method:methods){
			boolean isAnn = method.isAnnotationPresent(test.andann.ann.Method.class);
			if(!isAnn) continue;
			test.andann.ann.Method mAnn = method.getAnnotation(test.andann.ann.Method.class);
			int vI = mAnn.viewId();
			String name = mAnn.eventName();
			ViewModel mV = getViewModelById(subMap, vI);
			if("click".equals(name)){
				mV.setOnClickListener(new OnClickListener() {
					
					@Override
					public void click() {
						// TODO Auto-generated method stub
						try {
							method.invoke(mContent, null);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}else if("longclick".equals(name)){
				mV.setLongOnClickListener(new LongOnClickListener() {
					
					@Override
					public void longClick() {
						// TODO Auto-generated method stub
						try {
							method.invoke(mContent, null);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}else if("doubleclick".equals(name)){
				mV.setDoubleClick(new DoubleClick() {
					
					@Override
					public void doubleClick(ViewModel view) {
						try {
							method.invoke(mContent, view);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}
		
	}

	private static void initAttrs(Map<Integer,ViewModel> submap,Class<?> clasz) {
		Field[] fields = clasz.getFields();
		if(fields == null || fields.length == 0) return;
		for(Field field:fields){
			boolean isAnn = field.isAnnotationPresent(View.class);
			if(!isAnn) continue;
			View view = field.getAnnotation(View.class);
			int viewId = view.viewId();
			ViewModel viewModel = getViewModelById(submap, viewId);
			field.setAccessible(true);
			try {
				field.set(mContent, viewModel);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static void init(){
		if(map == null){
			map = new ConcurrentHashMap<Integer, Map<Integer,ViewModel>>();
		}
		subMap = new ConcurrentHashMap<Integer, ViewModel>();
		ViewModel view = new ViewModel();
		view.name = "init";
		subMap.put(123, view);
		map.put(1024, subMap);
	}


	public static Map<Integer,ViewModel> getMapById(int id){
		return map.get(id);
	}
	
	public static ViewModel getViewModelById(Map<Integer,ViewModel> submap,int id){
		return submap.get(id);
	}
	
}
