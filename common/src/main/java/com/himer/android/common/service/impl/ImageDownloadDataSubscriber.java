package com.himer.android.common.service.impl;

import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.himer.android.common.service.shell.ImageListener;
import com.himer.android.common.util.HLog;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/12/6.
 */
public class ImageDownloadDataSubscriber<T> extends BaseDataSubscriber<CloseableReference<T>> {

    private static final String TAG = ImageDownloadDataSubscriber.class.getSimpleName();

    private ImageListener mListener;

    public ImageDownloadDataSubscriber(ImageListener listener) {
        mListener = listener;
    }

    @Override
    protected void onNewResultImpl(DataSource<CloseableReference<T>> dataSource) {
        if (!dataSource.isFinished()) {
            return;
        }

        CloseableReference<T> imageReference = dataSource.getResult();
        if (imageReference != null) {
            try {
                T ret = imageReference.get();
                if (ret instanceof CloseableBitmap) {
                    handleCloseableImage((CloseableImage) ret);
                } else if (ret instanceof PooledByteBuffer) {
                    handlePooledByteBuffer((PooledByteBuffer) ret);
                } else {
                    mListener.onFaile("UNKNOWN", "unknown data type");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                CloseableReference.closeSafely(imageReference);
            }
        }
    }

    @Override
    protected void onFailureImpl(DataSource<CloseableReference<T>> dataSource) {


        Throwable throwable = dataSource.getFailureCause();
        HLog.exception(TAG, throwable);
        mListener.onFaile("UNKNOWN", throwable.toString());
    }

    private void handlePooledByteBuffer(PooledByteBuffer buff) {
        if (buff == null || buff.isClosed()) {
            mListener.onFaile("", "");
            return;
        }
        int length = buff.size();
        byte[] data = new byte[length];
        buff.read(0, data, 0, length);
        mListener.onSuccess(data);
    }

    private void handleCloseableImage(CloseableImage image) {

    }
}
