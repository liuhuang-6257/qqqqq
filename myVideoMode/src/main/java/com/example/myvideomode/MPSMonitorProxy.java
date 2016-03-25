package com.example.myvideomode;


import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MPSMonitorProxy {
    /**
     * 所有引用的接口对象
     */
    private final List<WeakReference<Object>> references = new LinkedList<WeakReference<Object>>();

    /**
     * 所有真实的监听者对象
     */
    private final Map<Class<?>, List<WeakReference<Object>>> realListeners = new HashMap<Class<?>, List<WeakReference<Object>>>();

    /**
     * 需要代理的接口
     */
    private final Class<?>[] interfaces;

    /**
     * 代理对象
     */
    private final Object proxy;

    /**
     * 构造器
     * @param listenerInterfaces
     *     需要代理的接口
     */
    public MPSMonitorProxy(final Class<?>... listenerInterfaces) {
    	int len = listenerInterfaces != null ? listenerInterfaces.length : 0;
    	
        if (len == 0) {
            throw new IllegalArgumentException("Listeners list cannot be empty");
        }

        for (final Class<?> listener : listenerInterfaces) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener class cannot be null");
            }
            if (!listener.isInterface()) {
                throw new IllegalArgumentException("Listener class should be an interface");
            }
        }

        interfaces = listenerInterfaces;

        proxy = Proxy.newProxyInstance(this.getClass().getClassLoader(), interfaces, new Handler());
    }

    /**
     * 增加目标监听者对象
     * @param listener
     */
    public void addListener(final Object listener) {
        if (listener != null) {
            WeakReference<Object> ref = new WeakReference<Object>(listener);
            for (WeakReference<Object> r : references) {
                if (r.get() == listener) {
                    return;
                }
            }

            references.add(ref);
            for (final Class<?> listenerClass : interfaces) {
                if (listenerClass.isInstance(listener)) {
                    List<WeakReference<Object>> list = realListeners.get(listenerClass);
                    if (list == null) {
                        list = new LinkedList<WeakReference<Object>>();
                        realListeners.put(listenerClass, list);
                    }
                    list.add(ref);
                }
            }
        }
    }

    /**
     * 移除目标监听者对象
     * @param listener
     */
    public void removeListener(final Object listener) {
        if (listener != null) {
            WeakReference<Object> ref = null;
            for (WeakReference<Object> r : references) {
                if (r.get() == listener) {
                    ref = r;
                    break;
                }
            }
            if (ref != null) {
                references.remove(ref);
                for (final Class<?> listenerClass : interfaces) {
                    if (listenerClass.isInstance(listener)) {
                        final List<WeakReference<Object>> list = realListeners.get(listenerClass);
                        if (list != null) {
                            list.remove(ref);
                        }
                    }
                }
            }
        }
    }

    /**
     * 移除所有监听者对象
     */
    public void removeAllListeners() {
        references.clear();
        for (final List<WeakReference<Object>> list : realListeners.values()) {
            list.clear();
        }
        realListeners.clear();
    }

    /**
     * 将泛型监听者强制转换成真实的监听者对象
     * @return
     */
    @SuppressWarnings("unchecked")
	public <Listener> Listener getListener() {
        return (Listener) proxy;
    }

    /**
     * 动态代理实现
     */
    private class Handler implements InvocationHandler {
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final Class<?> listenerClass = method.getDeclaringClass();
            final List<WeakReference<Object>> list = realListeners.get(listenerClass);

            if (list != null && !list.isEmpty()) {
                for (final WeakReference<Object> ref : list) {
                    Object listener = ref.get();
                    if (listener != null) {
                        method.invoke(listener, args);
                    }
                }
            }

            return null;
        }
    }
}
