package com.helpershigh;

public interface UI {
    static UI getUI() {
        return new UIImpl();
    }

    void uiService();
}
