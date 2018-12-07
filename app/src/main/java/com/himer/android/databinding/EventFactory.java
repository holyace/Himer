package com.himer.android.databinding;

import com.himer.android.databinding.event.DeleteSoundHandler;
import com.himer.android.databinding.event.DownloadSoundHandler;
import com.himer.android.databinding.event.PlaySoundHandler;
import com.himer.android.databinding.event.PreviewImageHandler;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class EventFactory implements EventConstants {

    public static IEventHandler getEventHandler(String type) {
        IEventHandler handler = null;
        switch (type) {
            case EVENT_PLAY:
                handler = new PlaySoundHandler();
                break;

            case EVENT_DOWNLOAD:
                handler = new DownloadSoundHandler();
                break;

            case EVENT_DELETE:
                handler = new DeleteSoundHandler();
                break;

            case EVENT_VIEW_IMAGE:
                handler = new PreviewImageHandler();
                break;

            default:
                break;
        }

        return handler;
    }
}
