package com.giri.target.svr;

import java.util.HashMap;
import java.util.Map;

public final class ThreadContext{
	
	
	////// Class members /////
	private final HashMap<String, Object> map = new HashMap<String, Object>();
	private ThreadContext(){
		
	}
	
	
	/////// Static members /////
	private static final long serialVersionUID = 7153910585716747283L;
	private static final ThreadLocal<ThreadContext> threadlocal = new ThreadLocal<ThreadContext>();

	public static void clear(){
		threadlocal.remove();
	}
	
	public static void set(final String key, final Object value){
		ThreadContext ctx = threadlocal.get();
		if(ctx == null){
			ctx = new ThreadContext();
			threadlocal.set(ctx);
		}
		ctx.map.put(key, value);
	}

	public static Object get(final String key){
		final ThreadContext ctx = threadlocal.get();
		return (ctx == null ? null : ctx.map.get(key));
	}
	
	public static Map<String, Object> getAll(){
		final ThreadContext ctx = threadlocal.get();
		return (ctx == null ? null : ctx.map);
	}
}
