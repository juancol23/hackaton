package weekendfever.riva.com.view.fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import weekendfever.riva.com.R;
import weekendfever.riva.com.SplashActivity;
import weekendfever.riva.com.view.menu.Dashboard;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCiudad extends Fragment {


    public FragmentCiudad() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_ciudad, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        }, 500);

        return view;
    }

}
