package pl.setblack.javafun.ratschool.lifelog;


import com.fasterxml.jackson.annotation.JsonCreator;

public class ItemIn {
    public final long points;
    public final String info;

    @JsonCreator
    public ItemIn(long points, String info) {
        this.points = points;
        this.info = info;
    }
}
