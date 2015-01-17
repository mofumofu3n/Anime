package com.ichif1205.anime.model;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class ArticleTest {


    @Test
    public void testSample() {
        Assert.assertEquals("a", "B");
    }
}
