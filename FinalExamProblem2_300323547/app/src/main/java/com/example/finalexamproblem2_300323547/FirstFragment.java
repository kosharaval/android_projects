package com.example.finalexamproblem2_300323547;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    private FirstFragmentListener listener;
    int textSize = 0;
    int initalProgress = 0;

    public interface FirstFragmentListener {
        void onSeekBarChange(int inputTextSize,String inputString);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        EditText editTextInputText = view.findViewById(R.id.editTextInputText);
        SeekBar seekBarSizeRange = view.findViewById(R.id.seekBarSizeRange);
        Button buttonChnageText = view.findViewById(R.id.buttonChnageText);

        buttonChnageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editTextInputText.getText().toString();
                listener.onSeekBarChange(textSize,inputText);
            }
        });

        seekBarSizeRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = textSize + (progress - initalProgress);
                initalProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FirstFragmentListener) {
            listener = (FirstFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}