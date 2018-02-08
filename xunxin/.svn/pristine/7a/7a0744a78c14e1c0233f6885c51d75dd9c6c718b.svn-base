package com.xunxin.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <p>反射工具类</p>
 * <p></p>	
 * <p>User: Zhugong Team
 * <p>Date: 2016年12月17日 下午4:49:52
 * <p>Version: 1.0
 */
public class ReflectUtils {

	/**
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> findParamterizedType(Class<?> clazz,int index){
		Type paramterizedType = clazz.getGenericSuperclass();
		//CGLUB subclass target object(泛型在父类上)
		if(!(paramterizedType instanceof ParameterizedType)){
			paramterizedType = clazz.getSuperclass().getGenericSuperclass();
		}
		if(!(paramterizedType instanceof ParameterizedType)){
			return null;
		}
		Type[] actualTypeArguments = ((ParameterizedType) paramterizedType).getActualTypeArguments();
		if(actualTypeArguments == null || actualTypeArguments.length == 0){
			return null;
		}
		return (Class<T>) actualTypeArguments[0];
	}
	
	/**
	 * 合并俩个对象，并验证get、set方法是否一致
	 * @param targetBean 
	 * @param sourceBean
	 * @return
	 */
	public static Object mergeEneity(Object targetBean, Object sourceBean){
		if(targetBean == null || sourceBean == null){
			return targetBean;
		}
		
		Method[] sourceBeanMethods = sourceBean.getClass().getMethods();
		Method[] targetBeanMethods = targetBean.getClass().getMethods();
		
		for(Method m : sourceBeanMethods){
			if(m.getName().indexOf("get") > -1){
				String set = m.getName().replaceFirst("g", "s");
				
				for(Method t : targetBeanMethods){ 
					if(set.equals(t.getName())){
						try {
							Object res = m.invoke(sourceBean);
							if(null != res){
								t.invoke(targetBean, res);
							}
							break;
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}         
		return targetBean;
	}
	
	/**
	 * 验证俩个方法返回值参数类型是否相同
	 * @param t
	 * @param m
	 * @return
	 */
	@SuppressWarnings("unused")
	private static boolean hasOneParamterAndTypeEquals(Method t,Method m){
		System.out.println(m.getName()+":"+m.getReturnType().getName());
		System.out.println(t.getName()+":"+t.getParameterTypes()[0].getName());
		System.out.println("----------------------------------------");
		
		return t.getParameterTypes().length == 1 && m.getReturnType().getName().equals(t.getParameterTypes()[0].getName());
	}
	
	
	
}
