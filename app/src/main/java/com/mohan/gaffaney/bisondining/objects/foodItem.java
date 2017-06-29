package com.mohan.gaffaney.bisondining.objects;

/**
 * Created by Ben on 6/25/2017.
 */

public class foodItem {
    public String itemName;
    public boolean isFavorite;

    public foodItem(String name, boolean favorite) {
        this.itemName = name;
        this.isFavorite = favorite;
    }

    @Override
    public String toString(){
        return this.itemName + ", " + this.isFavorite;
    }

    public void toggleFavorite(){
        if(isFavorite){
            isFavorite = false;
        } else{
            isFavorite = true;
        }
    }
}
