package com.ichif1205.anime;

import com.squareup.otto.Bus;

public class BusHolder {
    private static Bus sBus = new Bus();

    private BusHolder() {
    }

    public static Bus get() {
        return sBus;
    }
}
