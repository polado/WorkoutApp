package com.example.workoutapp.ui.login;

import android.util.Log;

import com.example.workoutapp.base.BasePresenter;
import com.example.workoutapp.base.BaseView;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

interface LoginView extends BaseView {
    void onReceiveData(boolean result);

    void userExists(boolean result, String name, String email);
}

public class LoginPresenter extends BasePresenter {
    private LoginView view;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    public LoginPresenter(LoginView view) {
        super(view);
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    void register(final String name, final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(name, email);
                        } else {
                            view.hideLoading();
                            view.showErrorMsg("Authentication failed");
                            view.onReceiveData(false);
                        }
                    }
                });
    }

    void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            view.onReceiveData(true);
                        } else {
                            view.hideLoading();
                            view.showErrorMsg("Authentication failed");
                            view.onReceiveData(false);
                        }
                    }
                });
    }

    void loginWithFacebook(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            getUser(user.getDisplayName(), user.getEmail().toLowerCase());
                        } else {
                            view.showErrorMsg("Authentication failed");
                            view.onReceiveData(false);
                        }
                    }
                });
    }

    private void getUser(final String name, final String email) {
        firestore.collection("users")
                .document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        view.userExists(true, name, email);
                    } else {
                        view.userExists(false, name, email);
                    }
                } else {
                    view.userExists(false, name, email);
                }
            }
        });
    }

    void createUser(String name, String email) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Name", name);
        userMap.put("email", email.toLowerCase());

        firestore.collection("users").document(email)
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("REGISTER", "DocumentSnapshot added with ID: ");

                        view.onReceiveData(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("REGISTER", "Error adding document", e);
                    }
                });
    }
}
