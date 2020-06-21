package com.example.workoutapp.ui.login;

import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.databinding.ActivityLoginBinding;
import com.example.workoutapp.ui.home.HomeActivity;
import com.example.workoutapp.utils.Utils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LoginActivity extends BaseActivity implements LoginView {
    ActivityLoginBinding binding;

    private CallbackManager facebookCallbackManager;

    private LoginPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new LoginPresenter(this);

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == binding.rbRegister.getId()) {
                    binding.ilName.setVisibility(View.VISIBLE);
                } else {
                    binding.ilName.setVisibility(View.GONE);
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    showLoading();
                    String email = binding.etEmail.getText().toString().trim().toLowerCase();
                    String password = binding.etPassword.getText().toString().trim();
                    if (binding.rbRegister.isChecked()) {
                        String name = binding.etName.getText().toString().trim();
                        presenter.register(name, email, password);
                    } else {
                        presenter.login(email, password);
                    }
                }
            }
        });

        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                LoginManager.getInstance()
                        .logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            }
        });

        setupFacebookSignIn();
    }

    private void setupFacebookSignIn() {
        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(
                facebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        presenter.loginWithFacebook(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        hideLoading();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        hideLoading();
                        showErrorMsg(exception.getMessage());
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onReceiveData(boolean result) {
        hideLoading();
        if (result) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void userExists(boolean result, String name, String email) {
        if (!result)
            presenter.createUser(name, email);
        else
            onReceiveData(true);
    }

    boolean isValid() {
        if (binding.rbRegister.isChecked()) {
            if (!Utils.isNotEmpty(binding.etName.getText().toString(), "Name can't be empty", this))
                return false;
        }
        if (!Utils.isNotEmpty(binding.etEmail.getText().toString(), "Email can't be empty", this))
            return false;

        if (!Utils.isNotEmpty(binding.etPassword.getText().toString(), "Password can't be empty", this))
            return false;

        return true;
    }
}
