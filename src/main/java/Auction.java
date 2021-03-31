import java.util.Date;
import java.util.List;

public class Auction {

    public Auction(int id, List<Object> objects, Date startDate, int minRankEntry) {
        this.id = id;
        this.objects = objects;
        this.startDate = startDate;
        this.minRankEntry = minRankEntry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getMinRankEntry() {
        return minRankEntry;
    }

    public void setMinRankEntry(int minRankEntry) {
        this.minRankEntry = minRankEntry;
    }

    private int id;
    private List<Object> objects;
    private Date startDate;
    private int minRankEntry;
}
