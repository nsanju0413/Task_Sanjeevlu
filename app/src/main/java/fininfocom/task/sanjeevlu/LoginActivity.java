package fininfocom.task.sanjeevlu;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout username;
    TextInputLayout password;
    Button login;
    FirebaseAuth auth;
    DatabaseReference dRef;
    String user,pwd;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    ".{7}" +               //at least 8 character
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwrord);
        login = findViewById(R.id.login);
        dRef = FirebaseDatabase.getInstance().getReference("users");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                //login();

            }
        });
    }

    private void validate() {
        validateUsername();
        ValidatePassword();
        if((validateUsername()==true)&&(ValidatePassword()==true))
        {
            login();
        }
    }

    private boolean ValidatePassword()
    {
     String  passwordVal =password.getEditText().getText().toString();
     if(passwordVal.isEmpty()){
         password.setError("Password Cant be empty");
         return false;
     }else if(!PASSWORD_PATTERN.matcher(passwordVal).matches()) {
         password.setError("Password does not match the required pattern");
         return false;
     }
     else {
         password.setError(null);
         return true;
     }
    }
    private boolean validateUsername() {
        String usernameInput = username.getEditText().getText().toString().trim();

        if (usernameInput.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() != 10) {
            username.setError("Username must be 10 characters");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }
    private void login() {
        user = username.getEditText().getText().toString();
        pwd = password.getEditText().getText().toString();
        dRef.child("admin").addListenerForSingleValueEvent(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()) {
                 String uid = snapshot.child("username").getValue(String.class);
                 String pswd = snapshot.child("password").getValue(String.class);
                if ((uid.equals(user)) && (pswd.equals(pwd))) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Credentials not found on DB", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(LoginActivity.this, "DB Does not Exist", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
