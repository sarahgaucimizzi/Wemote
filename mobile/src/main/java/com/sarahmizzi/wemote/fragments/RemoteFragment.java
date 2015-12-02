package com.sarahmizzi.wemote.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.sarahmizzi.wemote.R;

public class RemoteFragment extends Fragment {
    private ImageButton mButtonUp;
    private ImageButton mButtonDown;
    private ImageButton mButtonLeft;
    private ImageButton mButtonRight;
    private ImageButton mButtonOK;
    private Button mButtonExit;

    private OnButtonPressedListener mListener;
    public RemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_remote, container, false);
        mButtonUp = (ImageButton) view.findViewById(R.id.remote_up_button);
        mButtonDown = (ImageButton) view.findViewById(R.id.remote_down_button);
        mButtonLeft = (ImageButton) view.findViewById(R.id.remote_left_button);
        mButtonRight = (ImageButton) view.findViewById(R.id.remote_right_button);
        mButtonOK = (ImageButton) view.findViewById(R.id.remote_ok_button);
        mButtonExit = (Button) view.findViewById(R.id.remote_exit_button);


        mButtonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("UP");
            }
        });

        mButtonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("DOWN");
            }
        });

        mButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("LEFT");
            }
        });

        mButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("RIGHT");
            }
        });

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("OK");
            }
        });

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed("EXIT");
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnButtonPressedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnButtonPressedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnButtonPressedListener {
        public void buttonPressed(String s);
    }
}
