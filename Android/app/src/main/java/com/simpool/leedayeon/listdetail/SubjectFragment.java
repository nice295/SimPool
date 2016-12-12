package com.simpool.leedayeon.listdetail;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by LeeDaYeon on 2016-08-27.
 */
public class SubjectFragment extends Fragment {
    public  static EditText subj;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.subject_fragment, container, false );
        subj = (EditText)view.findViewById(R.id.editText5);
        return view;
    }
}
