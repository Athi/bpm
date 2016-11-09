package com.bpm.presentation.main;

import com.bpm.presentation.BPM;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;

import static com.bpm.presentation.main.MainView.VIEW_ID;

/**
 * Created by Athi
 */
@SpringView(name = VIEW_ID)
public class MainView extends MVerticalLayout implements View {

    public static final String VIEW_ID = "";

    @PostConstruct
    private void init() {
        with(new MButton("Logout").withListener(clickEvent -> BPM.getCurrent().logout()));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
