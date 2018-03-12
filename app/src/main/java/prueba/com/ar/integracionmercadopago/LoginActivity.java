package prueba.com.ar.integracionmercadopago;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mPasswordView, mEmailView;
    private View mProgressView, mLoginFormView;
    private FirebaseAuth mAuth;
    private Button mSignInButton, mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.log_in_button);
//        mSignInButton = findViewById(R.id.sign_in_button);

        mAuth = FirebaseAuth.getInstance();

       /* mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });
*/
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });

        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void Registrar() {
        mAuth.createUserWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void IniciarSesion(){
        mAuth.signInWithEmailAndPassword(mEmailView.getText().toString(), mPasswordView.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user.isEmailVerified()) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Snackbar.make(mLoginFormView, "Se ha enviado la validacion de mail", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

}

