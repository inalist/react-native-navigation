package com.reactnativenavigation.params.parsers;

import android.os.Bundle;

import com.reactnativenavigation.params.NavigationParams;
import com.reactnativenavigation.params.SlidingOverlayParams;

public class SlidingOverlayParamsParser extends Parser {

    public SlidingOverlayParams parse(Bundle bundle) {
        final SlidingOverlayParams result = new SlidingOverlayParams();
        result.screenInstanceId = bundle.getString("screen");
        Bundle navigationParamsBundle = bundle.getBundle("navigationParams");
        if (navigationParamsBundle != null) {
            result.navigationParams = new NavigationParams(navigationParamsBundle);
        }
        result.autoDismissTimerSec = bundle.containsKey("autoDismissTimerSec")
                ? bundle.getInt("autoDismissTimerSec")
                : null;
        result.position = SlidingOverlayParams.Position.fromString(bundle.getString("position", "top"));
        return result;
    }
}
