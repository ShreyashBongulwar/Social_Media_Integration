package com.example.social_media_inegration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Registration_Activity extends AppCompatActivity {
    TextView AlreadyHaveAccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        AlreadyHaveAccount=findViewById(R.id.Register_AlreadyHveAccount);
        inputEmail=findViewById(R.id.Register_Email);
        inputPassword=findViewById(R.id.Register_Password);
        inputConfirmPassword=findViewById(R.id.Register_ConfirmPassword);
        btnRegister=findViewById(R.id.Register_Here);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAuth();
            }
        });
    }

    private void performAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();
        if(!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        }
        else if(password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter Proper Password");
        }
        else if(!password.equals(confirmPassword))
        {
            inputConfirmPassword.setError("Password is not Matching");
        }
        else
        {
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();



            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUsertoNextAvtivity();
                        Toast.makeText(Registration_Activity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Registration_Activity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    private void sendUsertoNextAvtivity() {
        Intent intent=new Intent(Registration_Activity.this,Home_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}