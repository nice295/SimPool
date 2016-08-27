package com.example.leedayeon.listdetail;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LeeDaYeon on 2016-08-27.
 */
public class SubjectFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.subject_fragment, container, false );
        return view;
    }
}
