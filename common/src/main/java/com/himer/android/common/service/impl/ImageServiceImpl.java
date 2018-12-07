package com.himer.android.common.service.impl;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.himer.android.common.concurrent.BlockResult;
import com.himer.android.common.concurrent.HMExecutor;
import com.himer.android.common.concurrent.SafeJob;
import com.himer.android.common.service.shell.IImageService;
import com.himer.android.common.service.shell.ImageListener;
import com.himer.android.common.util.HLog;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/16.
 */
public class ImageServiceImpl implements IImageService {

    private static final String TAG = ImageServiceImpl.class.getSimpleName();

    private ExecutorSupplier mExcutor;

    @Override
    public void bindImage(final ImageView view, final String url) {
        if (view instanceof SimpleDraweeView) {
            ((SimpleDraweeView) view).setImageURI(url);
        } else {
            asyncDownload(url, new ImageListener() {
                @Override
                public void onSuccess(final Bitmap bitmap) {
                    if (bitmap == null) {
                        return;
                    }
                    HMExecutor.runUiThread(new SafeJob() {
                        @Override
                        public void safeRun() {
                            view.setImageBitmap(bitmap);
                        }
                    });
                }

                @Override
                public void onFaile(String errorCode, String errorMessage) {
                    HLog.e(TAG, "bindImage onFaile ", errorCode, errorMessage, url);
                }
            });
        }
    }

    @Override
    public void asyncDownload(String url, ImageListener listener) {
        Uri uri = Uri.parse(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        ImageRequest imageRequest = builder.build();
        DataSource<CloseableReference<PooledByteBuffer>> dataSource =
                imagePipeline.fetchEncodedImage(imageRequest, null);
        dataSource.subscribe(new ImageDownloadDataSubscriber<PooledByteBuffer>(listener),
                mExcutor.forBackgroundTasks());
    }

    @Override
    public Bitmap syncDownload(String url) {
        final BlockResult<Bitmap> result = new BlockResult<>();
        Uri uri = Uri.parse(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        ImageRequest imageRequest = builder.build();
        DataSource<CloseableReference<CloseableImage>> dataSource =
                imagePipeline.fetchDecodedImage(imageRequest, null);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                Throwable throwable = dataSource.getFailureCause();
                HLog.exception(TAG, throwable);
            }

            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);
                result.set(newBitmap);
            }
        }, mExcutor.forBackgroundTasks());
        return result.get(3000l);
    }

    @Override
    public void init(Application app, Map<String, Object> params) {
        mExcutor = new DefaultExecutorSupplier(4);
        Fresco.initialize(app);
    }

    @Override
    public void destory() {

    }
}
