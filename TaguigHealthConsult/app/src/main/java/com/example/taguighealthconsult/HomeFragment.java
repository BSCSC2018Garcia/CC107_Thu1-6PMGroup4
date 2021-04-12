package com.example.taguighealthconsult;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home,container, false);
        SliderView sliderView;
        List<ImageSliderModel>imageSliderModelList;
        imageSliderModelList = new ArrayList<>();
        sliderView = view.findViewById(R.id.imageSlider);
        imageSliderModelList.add(new ImageSliderModel(R.drawable.imagesliderone));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.imageslidertwo));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.imagesliderthree));

        sliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(),imageSliderModelList));
        ImageView appointment_btn = view.findViewById(R.id.appointment_btn);
        ImageView information_btn = view.findViewById(R.id.information_btn);

        appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appoint = new Intent(getActivity(), AppointmentActivity.class);
                startActivity(appoint);
            }
        });
        information_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(getActivity(), InformationActivity.class);
                startActivity(info);
            }
        });

   return view;
    }
}
