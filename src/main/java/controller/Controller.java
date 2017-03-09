package controller;

import fxapp.Main;

public abstract class Controller { // controller superclass
    // abstract because we don't want to initialize, just a generic layout
    protected Main mainApp;
    // protected so it is accessible to subclasses (all other controller classes)

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
