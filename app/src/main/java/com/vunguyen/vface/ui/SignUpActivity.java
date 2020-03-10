/*
 * SignUpActivity.java
 */
package com.vunguyen.vface.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vunguyen.vface.R;

import org.w3c.dom.Text;

/**
 * This class is to implements the functions for Sign Up account activity
 */
public class SignUpActivity extends AppCompatActivity
{
    ImageView ivBackArrow;
    TextView tvLogin;
    EditText emailID, password;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // set hide notification bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        // register the account with firebase server
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.emailID);
        password = findViewById(R.id.password);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v ->
        {
            String email = emailID.getText().toString();
            String pwd = password.getText().toString();

            if (email.isEmpty())
            {
                emailID.setError("Please enter your email");
                emailID.requestFocus();
            }
            else if(pwd.isEmpty())
            {
                password.setError("Please enter your password");
                password.requestFocus();
            }
            else if (email.isEmpty() && pwd.isEmpty())
            {
                Toast.makeText(SignUpActivity.this, "Fields are empty!",Toast.LENGTH_SHORT).show();
            }
            else if (!email.isEmpty() && !pwd.isEmpty())
            {
               mFirebaseAuth.createUserWithEmailAndPassword(email,pwd)
                       .addOnCompleteListener(SignUpActivity.this, task ->
                       {
                           if (!task.isSuccessful())
                           {
                               Toast.makeText(SignUpActivity.this, "Register failed. Please try again!"
                                       ,Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               Toast.makeText(SignUpActivity.this, "Registered Successful. Please log in."
                                       , Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                           }
                       });
            }
            else
            {
                Toast.makeText(SignUpActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        // go back to welcome screen
        ivBackArrow = findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, WelcomeScreenActivity.class );
            openActivity(intent);
        });

        // go to login screen
        tvLogin = findViewById(R.id.txtLogin);
        tvLogin.setOnClickListener(v ->
        {
            Intent intent = new Intent(this, LoginActivity.class);
            openActivity(intent);
        });
    }

    public void openActivity(Intent intent)
    {
        startActivity(intent);
        finish();
    }
}
