package com.example.pointme;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.example.pointme.activities.MainActivity;
import com.example.pointme.fragments.ListOfServiceProvidersFragment;
import com.example.pointme.models.ProvidersInfo;
import com.example.pointme.utils.SharedPreference;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class sharedPreferenceUtilTests {

    @Rule
    public ActivityTestRule<ListOfServiceProvidersFragment> mActivityRule = new ActivityTestRule<>(ListOfServiceProvidersFragment.class);

    @Test
    public void testRemoveFavorites() {

        Context context = mActivityRule.getActivity().getBaseContext();
        ProvidersInfo info = new ProvidersInfo();
        info.setEmail("unit@tests.com");
        info.setName("UnitTests");
        info.setSurname("example");

        SharedPreference sp = new SharedPreference();
        sp.removeFavorite(context, info);
    }

   /* @Test
    public void testConvertCelsiusToFahrenheit() {
        float actual = ConverterUtil.convertFahrenheitToCelsius(212);
        // expected value is 100
        float expected = 100;
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed", expected, actual, 0.001);
    }*/

}