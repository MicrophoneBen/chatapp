package com.ghostben.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Chatapp extends CustomComponent implements View {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final List<Msg> MSGS = new ArrayList<>();

    private static final ListDataProvider<Msg> DP = new ListDataProvider<>(MSGS);

    private static int UCOUNT = 1;

    private VerticalLayout vlytbaseLayout;

    int counter;
    Button send;

    public Chatapp() {
        send = new Button("send");
        send.setClickShortcut(KeyCode.ENTER);
        vlytbaseLayout = new VerticalLayout();

        setCompositionRoot(vlytbaseLayout);

        Grid<Msg> grid = new Grid<>();

        grid.addColumn(Msg::getMsg).setCaption("Chat");

        grid.setDataProvider(DP);

        Label lbl = new Label();
        lbl.setCaption("SecretUser" + UCOUNT++);
        TextField tf = new TextField();
        tf.setWidth(200, Unit.PERCENTAGE);

        Button send = new Button("send");
        send.setClickShortcut(KeyCode.ENTER);

        grid.addStyleName("chatgrid");

        HorizontalLayout hlyt = new HorizontalLayout();
        hlyt.addComponents(lbl, tf, send);
        hlyt.setExpandRatio(tf, 1);

        vlytbaseLayout.addComponents(grid, hlyt);
        vlytbaseLayout.setSizeFull();
        grid.setWidth(100, Unit.PERCENTAGE);

        send.addClickListener(e -> {
            grid.scrollToEnd();
            String value = tf.getValue();
            if (!"".equals(value.trim())) {
                MSGS.add(new Msg(new Date() + " " + lbl.getCaption() + " " + value));
                tf.clear();
                MyUI.ACTIVE_UIS.stream().forEach(ui -> {
                    ui.access(() -> {
                        DP.refreshAll();
                    });
                });
            }
        });
    }
}

class Msg {
    String msg;

    Msg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

