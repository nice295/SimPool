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
public class ObjectFragment extends Fragment {
   public  static EditText obj1;
   public  static EditText obj2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.object_fragment, container, false );
        obj1 = (EditText)view.findViewById(R.id.editText3);
        obj2 = (EditText)view.findViewById(R.id.editText4);
        return view;
    }
}
