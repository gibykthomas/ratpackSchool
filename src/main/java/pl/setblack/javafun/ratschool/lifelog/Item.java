package pl.setblack.javafun.ratschool.lifelog;

import java.time.LocalDateTime;

public class Item {
    public final long id;
    public final long points;
    public final String info;
    public final LocalDateTime time;

    public Item(long id, long points, String info, LocalDateTime time) {
        this.id = id;
        this.points = points;
        this.info = info;
        this.time = time;
    }

    public Item(ItemIn itemIn) {
        this(0,0,null, null);
    }

}
