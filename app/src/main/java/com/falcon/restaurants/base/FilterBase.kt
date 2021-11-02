package com.falcon.restaurants.base

import com.falcon.restaurants.utils.Logger

abstract class FilterBase<T> {

    private var copy: MutableList<T> = ArrayList()
    private var main: MutableList<T> = ArrayList()

    fun setList(original: MutableList<T>) {
        with(copy) { clear() ; addAll(original) }
    }

    fun filter(query: String): MutableList<T>{
        main.clear()

        if(query.isEmpty()){
            main.addAll(copy)
        }else{
            for(current in copy)
            {
                if(isQueryFound(query, current))
                {
                    main.add(current)
                }
            }
        }
        //Logger.log("TAG" , "query: ${query.isEmpty()} , main: ${main.size}" )

        return main
    }

    abstract fun isQueryFound(query: String ,current: T ): Boolean

}
