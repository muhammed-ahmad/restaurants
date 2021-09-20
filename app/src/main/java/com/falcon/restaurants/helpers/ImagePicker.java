package com.falcon.restaurants.helpers;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ImagePicker {

    Context context;

    @Inject
    public ImagePicker(Context context) {
        this.context = context;
    }

    public interface Listener{
        void onSuccess(Uri uri);
    }

    @SuppressLint("CheckResult")
    public void pick(Listener listener){

        RxImagePicker
                .create(MyImagePicker.class)
                .openGallery(context)
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        listener.onSuccess(result.getUri());
                    }
                });
    }

    public interface MyImagePicker {
        @Gallery
        Observable<Result> openGallery(Context context);

        @Camera
        Observable<Result> openCamera(Context context);
    }

}
