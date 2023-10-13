package com.example.diceexample.controller;

import android.util.Log;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.example.diceexample.viewmodel.DiceRollViewModel;
import com.example.diceexample.databinding.ActivityMainBinding;
import com.example.diceexample.model.Roll;

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
//    OnClickListener listener = (viewClicked) -> Log.d(getClass().getSimpleName(), ((Button) viewClicked).getText() + "clicked");
    //findViewById(R.id.third).setOnClickListener(listener); the way it was before.
    binding.roll.setOnClickListener((v) -> viewModel.rollDice(20,60));
    setupViewModel();
  }

  private void setupViewModel (){
    viewModel = new ViewModelProvider(this).get(DiceRollViewModel.class);
    viewModel.getDiceRoll()
        .observe(this, (rolls) -> {
          ArrayAdapter<Roll> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rolls);
          binding.rollValues.setAdapter(adapter);
        });
    viewModel.getThrowable()
        .observe(this, (throwable) -> {
          if (throwable != null) {
            Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
          }
        });
  }
}