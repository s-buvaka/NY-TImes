package com.example.indus.nytimes.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AboutView extends MvpView {

    void callPhone(String number);

    void sendMail(String email);

    void openWeb(String link);
}
