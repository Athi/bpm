package com.bpm.presentation;

import com.jarektoro.responsivelayout.ResponsiveColumn;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;

/**
 * Created by Athi
 */
public class BPMRoot extends ResponsiveLayout {

    private MCssLayout content;

    public BPMRoot() {
        setSizeFull();

        ResponsiveRow rootRow = addRow();
        rootRow.setHeight(100, Unit.PERCENTAGE);

        ResponsiveColumn menuColumn = rootRow.addColumn().withDisplayRules(12, 12, 2, 2);
        ResponsiveRow menuRow = new ResponsiveRow();
        menuColumn.setComponent(menuRow);

        ResponsiveColumn homeColumn = menuRow.addColumn()
                .withDisplayRules(12, 3, 12, 12)
                .withVisibilityRules(false, true, true, true)
                .withComponent(new MButton(FontAwesome.ALIGN_LEFT, "Logout", clickEvent -> BPM.getCurrent().logout()));

        ResponsiveColumn contentColumn = rootRow.addColumn().withDisplayRules(12, 12, 10, 10);

        content = new MCssLayout().withFullHeight().withFullWidth();
        contentColumn.setComponent(content);
    }

    public MCssLayout getContent() {
        return content;
    }
}
