package afniramadania.tech.githubconsumerapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import afniramadania.tech.githubconsumerapp.Activity.UserHomeActivity;

public class MainActivity extends AppCompatActivity {

    private static final int TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
            finish();
        }, TIME);
    }
}
