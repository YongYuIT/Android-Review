package com.thinking.marktest;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Yu Yong on 2018/3/26.
 */

public class AddClickListenerTestHandler {
    public static void process(final Object mainObject) throws Exception {
        Class<?> cl = mainObject.getClass();//com.thinking.marktest.MainActivity
        for (final Method marked_method : cl.getDeclaredMethods()) {
            //method --> setBtn_test
            AddClickListenerTestMark mark = marked_method.getAnnotation(AddClickListenerTestMark.class);
            if (mark != null) {
                //markObj --> @com.thinking.marktest.AddClickListenerTestMark(source=btn_test)
                final Field f_marked_obj = cl.getDeclaredField(mark.source());//Field of btn_test
                f_marked_obj.setAccessible(true);
                Object marked_obj = f_marked_obj.get(mainObject);//btn_test
                Object listener = Proxy.newProxyInstance(null, new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        //Object o --> btn_test
                        //Method method --> void onClick(View view)
                        //objects --> View view
                        return marked_method.invoke(mainObject, objects);
                    }
                });
                Method setOnClickListener = marked_obj.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                setOnClickListener.invoke(marked_obj, listener);
            }
        }

    }
}
