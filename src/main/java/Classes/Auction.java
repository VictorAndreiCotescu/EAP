package Classes;

import java.util.Date;
import java.util.List;

public class Auction {

    public Auction(int id, String objects, String startDate, int minRankEntry) {
        this.id = id;
        this.objects = objects;
        this.startDate = startDate;
        this.minRankEntry = minRankEntry;
    }

    public Auction() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjects() {
        return objects;
    }

    public void setObjects(String objects) {
        this.objects = objects;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getMinRankEntry() {
        return minRankEntry;
    }

    public void setMinRankEntry(int minRankEntry) {
        this.minRankEntry = minRankEntry;
    }

    private int id;
    private String objects;
    private String startDate;
    private int minRankEntry;
}
