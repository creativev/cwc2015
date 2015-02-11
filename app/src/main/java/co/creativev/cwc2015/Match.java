package co.creativev.cwc2015;

public class Match {
    public final String time;
    public final String country1;
    public final String score1;
    public final String overs1;
    public final String country2;
    public final String score2;
    public final String overs2;
    public final String venue;
    public final String result;

    public Match(String time, String country1, String score1, String overs1, String country2, String score2, String overs2, String venue, String result) {
        this.time = time;
        this.country1 = country1;
        this.score1 = score1;
        this.overs1 = overs1;
        this.country2 = country2;
        this.score2 = score2;
        this.overs2 = overs2;
        this.venue = venue;
        this.result = result;
    }
}
