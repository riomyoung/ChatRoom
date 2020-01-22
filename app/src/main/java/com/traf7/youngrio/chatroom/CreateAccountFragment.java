package com.traf7.youngrio.chatroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountFragment extends Fragment {

    private EditText mUsername;
    private EditText mPassword;
    private EditText mEmail;
    private View mProgressView;
    private View mCreateForm;

    /** Activity callback **/
    private ActivityCallback mCallback;

    /** Firebase objects **/
    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Create a instance of this fragment
     *
     * @return fragment instance
     */
    public static CreateAccountFragment newInstance() {
        return new CreateAccountFragment();
    }

    /// Lifecycle methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_account, container, false);

        mUsername = (EditText) root.findViewById(R.id.create_account_username);
        mPassword = (EditText) root.findViewById(R.id.create_account_password);
        mEmail = (EditText) root.findViewById(R.id.create_account_email);

        mCreateForm = root.findViewById(R.id.create_account_form);
        mProgressView = root.findViewById(R.id.create_account_progress);

        Button createButton = (Button) root.findViewById(R.id.create_account_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ActivityCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /// Private methods

    private void createAccount() {



        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        if ( email.equals( "" ) || password.equals("") )
        {
            Toast.makeText(getContext(), "Missing email or password!!", Toast.LENGTH_SHORT).show();
        }
        else {
            showProgress(true);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(getContext(), R.string.error_create_account, Toast.LENGTH_SHORT).show();
                    } else {
                        Utils.saveLocalUser(getContext(),
                                mUsername.getText().toString(),
                                mEmail.getText().toString(),
                                task.getResult().getUser().getUid());

                        mCallback.openChat();
                    }

                    showProgress(false);
                    Utils.closeKeyboard(getContext(), mEmail);
                }
            });
        }
    }

    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mCreateForm.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
