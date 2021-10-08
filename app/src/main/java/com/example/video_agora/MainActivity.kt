package com.example.video_agora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout

import io.agora.rtc2.Constants
import io.agora.agorauikit_android.*
class MainActivity : AppCompatActivity() {
    // Fill the App ID of your project generated on Agora Console.
    private val appId = "34435d200dab41c7895b2f007f2b609a"

    // Fill the temp token generated on Agora Console.
    private val token = "00634435d200dab41c7895b2f007f2b609aIAB+EFEDYK7P99pvCfmrQeqpETjho7SMVktdGvBSPLk77dfYYSgAAAAAEADy5cWPzWlhYQEAAQDNaWFh"

    // Fill the channel name.
    private val channelName = "join"

    private var agView: AgoraVideoViewer? = null
    private fun initializeAndJoinChannel(){
        // Create AgoraVideoViewer instance
        try {
            agView = AgoraVideoViewer(
                this, AgoraConnectionData(appId),
            )
        } catch (e: Exception) {
            print("Could not initialize AgoraVideoViewer. Check your App ID is valid.")
            print(e.message)
            return
        }
        // Fill the parent ViewGroup (MainActivity)
        this.addContentView(
            agView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Check permission and join channel
        if (AgoraVideoViewer.requestPermissions(this)) {
            agView!!.join(channelName, token = token, role = Constants.CLIENT_ROLE_BROADCASTER)
        }

        else {
            val joinButton = Button(this)
            joinButton.text = "Allow Camera and Microphone, then click here"
            joinButton.setOnClickListener(View.OnClickListener {
                // When the button is clicked, check permissions again and join channel
                // if permissions are granted.
                if (AgoraVideoViewer.requestPermissions(this)) {
                    (joinButton.parent as ViewGroup).removeView(joinButton)
                    agView!!.join(channelName, token = token, role = Constants.CLIENT_ROLE_BROADCASTER)
                }
            })
            joinButton.setBackgroundColor(Color.GREEN)
            joinButton.setTextColor(Color.RED)
            this.addContentView(joinButton, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeAndJoinChannel()
    }
}