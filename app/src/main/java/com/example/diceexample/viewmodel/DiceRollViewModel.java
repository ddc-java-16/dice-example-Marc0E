package com.example.diceexample.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.diceexample.model.Roll;
import com.example.diceexample.service.DiceRollRepository;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;

public class DiceRollViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

  private final MutableLiveData<Roll[]> diceRoll;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;
  private final DiceRollRepository repository;
  public DiceRollViewModel(@NotNull Application application) {
    super(application);
    diceRoll = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    repository = new DiceRollRepository();
  }

  public LiveData<Roll[]> getDiceRoll() {
    return diceRoll;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void rollDice(int numberOfDices, int numberOfSides){
    throwable.postValue(null);
    Disposable disposable = repository.rollDice(numberOfDices,numberOfSides)
        .subscribe(
            diceRoll::postValue, //lambda transformed into a method reference form.
            (t) -> throwable.postValue(t)
        );
    pending.add(disposable);
  }

  @Override
  public void onStop(@NotNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }
}
