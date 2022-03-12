package com.example.moviapp.Fragment;

import static com.example.moviapp.HomeActivity.ACCOUNT_USER;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviapp.Models.User;
import com.example.moviapp.R;
import com.example.moviapp.databinding.FragmentProfileBinding;

public class EditProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}