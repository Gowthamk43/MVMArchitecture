package com.company.demotest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.company.demotest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       /* binding.setViewModel(new AppViewModel());
        binding.executePendingBindings();*/
        AppViewModel appViewModel = new AppViewModel();
        binding.setViewModel(appViewModel);


        appViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (user.isEmailValid() && user.isPasswordLengthGreaterThan5()){
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,TestMVVMDemo.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}