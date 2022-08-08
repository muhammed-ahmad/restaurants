package com.falcon.restaurants.domain.interactor

class LocalListeners {

    interface OnSuccessListener{
        fun onSuccess(b: Boolean)
        fun onFailed()
    }
}
