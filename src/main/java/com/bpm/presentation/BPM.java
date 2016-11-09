package com.bpm.presentation;

import com.bpm.presentation.login.LoginForm;
import com.bpm.security.Security;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.bpm.presentation.BPM.APPLICATION_TITLE;

/**
 * Created by Athi
 */
@Title(APPLICATION_TITLE)
@Theme(ValoTheme.THEME_NAME)
@SpringUI
@PreserveOnRefresh
public class BPM extends UI {

    static final String APPLICATION_TITLE = "BPM";

    @Autowired
    private AuthenticationManager authenticationManager;

    private BPMRoot bpmRoot = new BPMRoot();
    private ComponentContainer viewDisplay = bpmRoot.getContent();

    @Autowired
    private SpringViewProvider springViewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        if (Security.isLoggedIn()) {
            showMain();
        } else {
            setContent(new LoginForm(this::login));
        }
    }

    public void logout() {
        getPage().reload();
        getSession().close();
    }

    public static BPM getCurrent() {
        return (BPM) UI.getCurrent();
    }

    private boolean login(String username, String password) {
        try {
            Authentication token = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            // Reinitialize the session to protect against session fixation attacks. This does not work
            // with websocket communication.
            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
            SecurityContextHolder.getContext().setAuthentication(token);
            // Now when the session is reinitialized, we can enable websocket communication. Or we could have just
            // used WEBSOCKET_XHR and skipped this step completely.
            getPushConfiguration().setTransport(Transport.WEBSOCKET);
            getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            // Show the main UI
            showMain();
            return true;
        } catch (AuthenticationException ex) {
            return false;
        }
    }

    private void showMain() {
        setContent(bpmRoot);
        Navigator navigator = new Navigator(this, viewDisplay);
        navigator.addProvider(springViewProvider);
    }

    private void handleError(com.vaadin.server.ErrorEvent event) {
        Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
        if (t instanceof AccessDeniedException) {
            Notification.show("You do not have permission to perform this operation", Notification.Type.ERROR_MESSAGE);
        } else {
            DefaultErrorHandler.doDefault(event);
        }
    }
}
