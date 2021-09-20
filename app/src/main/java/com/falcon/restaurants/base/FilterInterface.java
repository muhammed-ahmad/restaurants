package com.falcon.restaurants.base;

import java.util.ArrayList;
import java.util.List;

public abstract class FilterInterface<T> {

    private List<T> copy = new ArrayList<>();
    private List<T> main = new ArrayList<>();

    public void setList(List<T> main) {
        this.copy.addAll(main);
        this.main.addAll(main);
    }

    public List<T> filter(String query){
        main.clear();

        if(query.isEmpty()){
            main.addAll(copy);
        }else{
            for(T current: copy)
            {
                if(isQueryFound(query, current))
                {
                    main.add(current);
                }
            }
        }

        return main;
    }

    public abstract boolean isQueryFound(String query, T current);

}
