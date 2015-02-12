package co.creativev.cwc2015;

import java.util.Date;

public class Match {
    public final int id;
    public final String name;
    public final Date time;
    public final String country1;
    public final String score1;
    public final String overs1;
    public final String country2;
    public final String score2;
    public final String overs2;
    public final String venue;
    public final String winningTeam;
    public final String result;

    public Match(int id, String name, Date time,
                 String country1, String score1, String overs1,
                 String country2, String score2, String overs2,
                 String venue, String winningTeam, String result) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.country1 = country1;
        this.score1 = score1;
        this.overs1 = overs1;
        this.country2 = country2;
        this.score2 = score2;
        this.overs2 = overs2;
        this.venue = venue;
        this.winningTeam = winningTeam;
        this.result = result;
    }
}
