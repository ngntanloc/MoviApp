package com.example.moviapp.Fragment;

import static com.example.moviapp.HomeActivity.ACCOUNT_USER;

import android.accounts.Account;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.moviapp.HomeActivity;
import com.example.moviapp.Models.User;
import com.example.moviapp.R;
import com.example.moviapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    public static ProfileFragment getInstance(User user) {

        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ACCOUNT_USER, user);
        profileFragment.setArguments(bundle);

        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.getSerializable(ACCOUNT_USER);
            if (user != null) {
                binding.txtName.setText(user.getUserName());
                binding.txtEmail.setText(user.getEmail());

                if (user.getImgAvatar() != null) {
                    Glide.with(getActivity())
                            .load(user.getImgAvatar())
                            .into(binding.profileImage);
                } else {
                    binding.profileImage.setImageResource(R.mipmap.ic_launcher);
                }

            } else {
                binding.txtName.setText("NULL");
            }
        }

        binding.icEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return binding.getRoot();
    }
}
