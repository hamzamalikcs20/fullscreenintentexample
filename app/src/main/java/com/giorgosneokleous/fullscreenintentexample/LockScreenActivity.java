
package com.giorgosneokleous.fullscreenintentexample;

import android.os.Bundle;
import android.app.Activity;
import android.os.Build;

public class LockScreenActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        ActivityUtils.turnScreenOnAndKeyguardOff(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.turnScreenOffAndKeyguardOn(this);
    }
}
