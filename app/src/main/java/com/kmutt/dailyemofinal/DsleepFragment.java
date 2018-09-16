package com.kmutt.dailyemofinal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.security.auth.login.LoginException;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DsleepFragment extends Fragment {

    private static final String API_PREFIX = "https://api.fitbit.com";
    View view;

    private MainActivity mainActivity = new MainActivity();

    public DsleepFragment() {
        // Required empty public constructor
    }





}
