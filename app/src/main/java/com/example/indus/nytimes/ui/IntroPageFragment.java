package com.example.indus.nytimes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.indus.nytimes.R;
import com.example.indus.nytimes.utils.Const;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class IntroPageFragment extends Fragment {

    private static final String PAGE_NUMBER = "page_number";

    private int pageNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt(PAGE_NUMBER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_intro_page, container, false);

        ImageView imageContainer = view.findViewById(R.id.image_container);
        imageContainer.setImageResource(Const.INTRO_IMAGE_LIST[pageNumber]);

        return view;
    }

    static IntroPageFragment newInstance(int page) {
        IntroPageFragment fragment = new IntroPageFragment();
        Bundle args = new Bundle();
        args.putInt(PAGE_NUMBER, page);
        fragment.setArguments(args);

        return fragment;
    }
}
