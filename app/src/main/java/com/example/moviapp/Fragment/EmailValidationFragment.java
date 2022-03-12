package com.example.moviapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.moviapp.R;
import com.example.moviapp.databinding.FragmentEmailValidationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EmailValidationFragment extends Fragment {

    public EmailValidationFragment() {
    }

    private FragmentEmailValidationBinding binding;
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEmailValidationBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.edtEmail.getText().toString().isEmpty()) {
                    auth.sendPasswordResetEmail(binding.edtEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Bundle bundle = new Bundle();
                                        bundle.putString("email", binding.edtEmail.getText().toString());

                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frameLayout, new NotifyFragment());
                                        transaction.commit();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Please input your email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }
}
