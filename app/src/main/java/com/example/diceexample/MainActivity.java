package com.example.diceexample;

import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.example.diceexample.databinding.ActivityMainBinding;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private DiceRollViewModel viewModel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    //setContentView(R.layout.activity_main); the way it was before.
    setContentView(binding.getRoot());
//    Button first = findViewById(R.id.first);
//    first.setOnClickListener();
    OnClickListener listener = (viewClicked) -> Log.d(getClass().getSimpleName(), ((Button) viewClicked).getText() + "clicked");
    binding.first.setOnClickListener(listener);
    binding.second.setOnClickListener(listener);
    //findViewById(R.id.third).setOnClickListener(listener); the way it was before.
    binding.third.setOnClickListener((v) -> viewModel.rollDice(2,6));
    setupViewModel();
  }

  private void setupViewModel (){
    viewModel = new ViewModelProvider(this).get(DiceRollViewModel.class);
    viewModel.getDiceRoll().observe(this, (rolls) -> Log.d(getClass().getSimpleName(),
        Arrays.toString(rolls)));
    viewModel.getThrowable()
        .observe(this, (throwable) -> {
          if (throwable != null) {
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
          }
        });
  }
}