package com.project.ia.desktop;

import com.project.ia.OnlineServices;
import com.project.ia.data.Save;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Online implements OnlineServices {
    private static final Online ourInstance = new Online();

    public static Online getInstance() {
        return ourInstance;
    }

    private Online() {
    }

    @Override
    public void sendTeam() {
        String myString = Save.INSTANCE.loadTeam("main_team").serialize();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
