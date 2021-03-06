package com.example.starter.base;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;

/**
 * The main view contains a button and a click listener.
 */
@Route("user/whoami")
public class WhoAmIView extends VerticalLayout {

    @Inject
    GreetService greetService;

    public WhoAmIView(@RestClient WhoAmIService whoAmISvc) {
        System.out.println("WHO AM I");
        var userData = whoAmISvc.whoami();
        var username = userData.get("username");
        System.out.println(username);
        // Use TextField for standard text input
        TextField textField = new TextField("("+username+") Your name");
        textField.addThemeName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> Notification
                .show(greetService.greet(textField.getValue())));

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");
        
        add(textField, button);
    }
}
