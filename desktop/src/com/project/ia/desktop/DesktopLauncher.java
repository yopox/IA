package com.project.ia.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.project.ia.IAGame;
import com.project.ia.OnlineServices;
import com.project.ia.data.Save;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 640;
        config.height = 360;
        config.title = "IAProject — Desktop";
        new LwjglApplication(new IAGame(Online.getInstance()), config);
    }

}