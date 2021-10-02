package com.falcon.restaurants.helpers
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker.entity.sources.Camera
import com.qingmei2.rximagepicker.entity.sources.Gallery
import javax.inject.Inject
import io.reactivex.Observable
import io.reactivex.functions.Consumer

class ImagePicker @Inject constructor (val context: Context) {

    interface Listener{
        fun onSuccess(uri: Uri)
    }

    @SuppressLint("CheckResult")
    fun pick(listener: Listener){

            RxImagePicker
                .create(MyImagePicker::class.java)
                .openGallery(context)
                .subscribe(object :Consumer<Result> {
                    @Throws(Exception::class)
                    override fun accept(result: Result) {
                        listener.onSuccess(result.uri)
                    }
                })
    }

    interface MyImagePicker {
        @Gallery
        fun openGallery(context: Context): Observable<Result>
        @Camera
        fun openCamera(context: Context): Observable<Result>
    }

}
