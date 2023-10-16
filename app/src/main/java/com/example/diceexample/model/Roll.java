package com.example.diceexample.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;


public class Roll {

  public int getValue() {
    return value;
  }

  @Expose
  private int value;


  @NonNull
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
