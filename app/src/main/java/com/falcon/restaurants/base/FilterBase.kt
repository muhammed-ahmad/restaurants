package com.falcon.restaurants.base

abstract class FilterBase<T> {

    private var copy: MutableList<T> = ArrayList()
    private var main: MutableList<T>  = ArrayList()

    fun setList(main: MutableList<T>) {
        this.copy.addAll(main)
        this.main.addAll(main)
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

        return main
    }

    abstract fun isQueryFound(query: String ,current: T ): Boolean

}
