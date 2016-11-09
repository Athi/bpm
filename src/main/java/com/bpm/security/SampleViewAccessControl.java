package com.bpm.security;

import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.ui.UI;
import org.springframework.stereotype.Component;

/**
 * Created by Athi
 */
@Component
public class SampleViewAccessControl implements ViewAccessControl {

    @Override
    public boolean isAccessGranted(UI ui, String beanName) {
        if (beanName.equals("adminView")) {
            return Security.hasRole("ROLE_ADMIN");
        } else {
            return Security.hasRole("ROLE_USER");
        }
    }
}