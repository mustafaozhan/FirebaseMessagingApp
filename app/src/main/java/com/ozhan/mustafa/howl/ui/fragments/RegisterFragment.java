package com.ozhan.mustafa.howl.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.ozhan.mustafa.howl.R;
import com.ozhan.mustafa.howl.core.registration.RegisterContract;
import com.ozhan.mustafa.howl.core.registration.RegisterPresenter;
import com.ozhan.mustafa.howl.core.users.add.AddUserContract;
import com.ozhan.mustafa.howl.core.users.add.AddUserPresenter;
import com.ozhan.mustafa.howl.ui.activities.SettingsActivity;


/**
 * Created by Mustafa Özhan on 3/4/17 at 1:07 PM.
 */


public class RegisterFragment extends Fragment implements View.OnClickListener, RegisterContract.View, AddUserContract.View {
    private static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterPresenter mRegisterPresenter;
    private AddUserPresenter mAddUserPresenter;

    private EditText mETxtEmail, mETxtPassword;
    private Button mBtnRegister;

    private ProgressDialog mProgressDialog;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_register, container, false);
        bindViews(fragmentView);
        return fragmentView;
    }

    private void bindViews(View view) {
        mETxtEmail = (EditText) view.findViewById(R.id.edit_text_email_id);
        mETxtPassword = (EditText) view.findViewById(R.id.edit_text_password);
        mBtnRegister = (Button) view.findViewById(R.id.button_register);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mRegisterPresenter = new RegisterPresenter(this);
        mAddUserPresenter = new AddUserPresenter(this);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));
        mProgressDialog.setMessage(getString(R.string.please_wait));
        mProgressDialog.setIndeterminate(true);

        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.button_register:
                onRegister(view);
                break;
        }
    }

    private void onRegister(View view) {
        String emailId = mETxtEmail.getText().toString();
        String password = mETxtPassword.getText().toString();

        mRegisterPresenter.register(getActivity(), emailId, password);
        mProgressDialog.show();
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        mProgressDialog.setMessage(getString(R.string.adding_user_to_db));
        Toast.makeText(getActivity(), "Registration Successful!", Toast.LENGTH_SHORT).show();
        mAddUserPresenter.addUser(getActivity().getApplicationContext(), firebaseUser);
    }

    @Override
    public void onRegistrationFailure(String message) {
        mProgressDialog.dismiss();
        mProgressDialog.setMessage(getString(R.string.please_wait));
        Log.e(TAG, "onRegistrationFailure: " + message);
        Toast.makeText(getActivity(), "Registration failed!+\n" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAddUserSuccess(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        SettingsActivity.startActivity(getActivity());
    }

    @Override
    public void onAddUserFailure(String message) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
