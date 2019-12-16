package com.emerson.organizerapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emerson.organizerapp.R;
import com.emerson.organizerapp.adapters.GaleriaAdapter;
import com.emerson.organizerapp.interfaces.RecyclerViewOnClickListenerHack;
import com.emerson.organizerapp.model.GaleriaModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomGalleryDialog extends BottomSheetDialogFragment implements RecyclerViewOnClickListenerHack {
    private BottomGalleryDialog.BottomGalleryListener mListener;
    private RecyclerView rvGalley;
    private ImageView imgClose;
    private GaleriaAdapter galeriaAdapter;
    List<String> galeriaList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_gallery_layout, container, false);
        imgClose = v.findViewById(R.id.img_close);
        rvGalley = v.findViewById(R.id.rv_gallery);
        rvGalley.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(getContext(),3);
        glm.setOrientation(LinearLayoutManager.VERTICAL);
        rvGalley.setLayoutManager(glm);

        galeriaList = GaleriaModel.getCameraImages(getContext());

        galeriaAdapter = new GaleriaAdapter(getContext(), galeriaList);
        galeriaAdapter.setRecyclerViewOnClickListenerHack(this);
        rvGalley.setAdapter(galeriaAdapter);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        rvGalley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("btnCamera");
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    public interface BottomGalleryListener {
        void onButtonClicked(String text);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomGalleryDialog.BottomGalleryListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }
}
