import java.util.*;
import java.util.stream.Collectors;

public class OlympicsImpl implements Olympics {

    private Map<Integer, Competitor> competitors;
    private Map<Integer, Competition> competitions;

    public OlympicsImpl() {
        this.competitors = new HashMap<>();
        this.competitions = new HashMap<>();
    }


    @Override
    public void addCompetitor(int id, String name) {

        if (competitors.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        competitors.put(id, new Competitor(id, name));

    }

    @Override
    public void addCompetition(int id, String name, int score) {

        if (competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        competitions.put(id, new Competition(name, id, score));
    }

    @Override
    public void compete(int competitorId, int competitionId) {

        if (!competitions.containsKey(competitionId) || !competitors.containsKey(competitorId)) {
            throw new IllegalArgumentException();
        }

        Competition competition = competitions.get(competitionId);
        Competitor competitor = competitors.get(competitorId);
        competition.getCompetitors().add(competitor);
        int score = competition.getScore();
        long totalScore = competitor.getTotalScore();
        competitor.setTotalScore(totalScore + score);

    }

    @Override
    public void disqualify(int competitionId, int competitorId) {

        if (!competitions.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }

        Competition competition = competitions.get(competitionId);
        Collection<Competitor> competitors = competition.getCompetitors();
        Competitor competitor1 = competitors.stream().filter(c -> c.getId() == competitorId).findFirst().orElse(null);
        if (competitor1 == null) {
            throw new IllegalArgumentException();
        }

        Competitor competitor = this.competitors.get(competitorId);
        competition.getCompetitors().remove(competitor);
        int score = competition.getScore();
        long totalScore = competitor.getTotalScore();
        competitor.setTotalScore(totalScore - score);

    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {

        return competitors.values().stream().filter(c -> c.getTotalScore() > min && c.getTotalScore() <= max).collect(Collectors.toList());

    }

    @Override
    public Iterable<Competitor> getByName(String name) {
        List<Competitor> collect = competitors.values().stream().filter(c -> c.getName().equals(name)).collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }


        return collect.stream().sorted(Comparator.comparing(Competitor::getId)).collect(Collectors.toList());
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {

        List<Competitor> collect = competitors.values().stream().filter(c -> c.getName().length() >= minLength && c.getName().length() <= maxLength).collect(Collectors.toList());

        return collect.stream().sorted(Comparator.comparing(Competitor::getId)).collect(Collectors.toList());

    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {

        if (!competitions.containsKey(competitionId)) {
            throw new IllegalArgumentException();
        }

        Competition competition = competitions.get(competitionId);

        return competition.getCompetitors().contains(comp);

    }

    @Override
    public int competitionsCount() {

        return competitions.size();
    }

    @Override
    public int competitorsCount() {

        return competitors.size();
    }

    @Override
    public Competition getCompetition(int id) {

        if (!competitions.containsKey(id)) {
            throw new IllegalArgumentException();
        }

        return competitions.get(id);
    }
}
