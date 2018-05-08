package com.thinking.keep_alive;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static com.thinking.keep_alive.MyApp.ProcessName;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.thinking.keep_alive.action.FOO";
    private static final String ACTION_BAZ = "com.thinking.keep_alive.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.thinking.keep_alive.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.thinking.keep_alive.extra.PARAM2";

    private WindowManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        {
            WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            //不抢焦点
            wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            wmParams.gravity = Gravity.LEFT | Gravity.TOP;
            wmParams.width = 500;
            wmParams.height = 500;

            LayoutInflater inflater = LayoutInflater.from(getApplication());
            LinearLayout mFloatLayout = (LinearLayout) inflater.inflate(R.layout.test_window_layout, null);
            manager.addView(mFloatLayout, wmParams);
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Log.i("yuyong_test", ProcessName + "-->" + "try to start service");
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        //throw new UnsupportedOperationException("Not yet implemented");
        while (true) {
            Log.i("yuyong_test", ProcessName + "-->" + "handleActionBaz");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
