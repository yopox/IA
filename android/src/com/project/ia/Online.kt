package com.project.ia

import android.content.Intent
import com.badlogic.gdx.backends.android.AndroidApplication
import com.project.ia.data.Save

class Online(val androidApplication: AndroidApplication): OnlineServices {

    override fun sendTeam() {
        val sendIntent: Intent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, Save.loadTeam("main_team").serialize())
        sendIntent.type = "text/plain"
        androidApplication.startActivity(sendIntent)
    }

}