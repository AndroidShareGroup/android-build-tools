package com.android.tests.basic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ClassNotFoundException;
import java.lang.NoSuchMethodException;
import java.lang.reflect.Method;

public class Main extends Activity
{

    private int foo = 1234;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView tv = (TextView) findViewById(R.id.dateText);

        try {
            // use reflection to make sure the class wasn't obfuscated
            Class<?> theClass = Class.forName("com.android.tests.basic.StringGetter");
            Method method = theClass.getDeclaredMethod("getString", int.class);
            tv.setText((String) method.invoke(null, foo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * use reflection to get a method that should be obfuscated
     */
    public void getObfuscatedMethod() throws NoSuchMethodException{
        try {
            Class<?> theClass = Class.forName("com.android.tests.basic.StringGetter");
            Method method = theClass.getDeclaredMethod("getStringInternal", int.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
