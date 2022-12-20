package org.softuni.exam.structures;

import org.softuni.exam.entities.Actor;
import org.softuni.exam.entities.Movie;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class MovieDatabaseImpl implements MovieDatabase {

    private Map<Actor, List<Movie>> movieDatabase;
    private List<Movie> movies;
    private List<Actor> actors;


    public MovieDatabaseImpl() {
        this.movieDatabase = new LinkedHashMap<>();
        this.movies = new ArrayList<>();
        this.actors = new ArrayList<>();
    }


    @Override
    public void addActor(Actor actor) {
        movieDatabase.put(actor, new ArrayList<>());
        actors.add(actor);

    }

    @Override
    public void addMovie(Actor actor, Movie movie) throws IllegalArgumentException {

        if (!contains(actor)) {
            throw new IllegalArgumentException();
        }

        movieDatabase.get(actor).add(movie);
        movies.add(movie);
        actors.remove(actor);

    }

    @Override
    public boolean contains(Actor actor) {
        return movieDatabase.containsKey(actor);
    }

    @Override
    public boolean contains(Movie movie) {
        return movies.contains(movie);
    }

    @Override
    public Iterable<Movie> getAllMovies() {
        return movies;
    }

    @Override
    public Iterable<Actor> getNewbieActors() {
        return actors;
    }

    @Override
    public Iterable<Movie> getMoviesOrderedByBudgetThenByRating() {
//        Comparator<Movie> reversed = Comparator.comparing(Movie::getBudget).reversed().thenComparing(Movie::getRating).reversed();

        return movies.stream()
                .sorted((o1, o2) -> {
                    if (Double.compare(o2.getBudget(), o1.getBudget()) == 0) {
                        return Double.compare(o2.getRating(), o1.getRating());
                    }
                    return Double.compare(o2.getBudget(), o1.getRating());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Actor> getActorsOrderedByMaxMovieBudgetThenByMoviesCount() {
        return movieDatabase.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    double aDouble1 = o1.getValue().stream().map(Movie::getBudget).max(Comparator.comparing(budget -> budget)).get();
                    double aDouble2 = o2.getValue().stream().map(Movie::getBudget).max(Comparator.comparing(budget -> budget)).get();

//                    double budget = o1.getValue().stream().max(Comparator.comparing(Movie::getBudget)).get().getBudget();
//                    double budget1 = o2.getValue().stream().max(Comparator.comparing(Movie::getBudget)).get().getBudget();


                    if (Double.compare(aDouble1, aDouble2) == 0) {
                        return Integer.compare(o2.getValue().size(), o1.getValue().size());
                    }

                    return Double.compare(aDouble1, aDouble2);
                }).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Movie> getMoviesInRangeOfBudget(double lower, double upper) {
        return movies.stream()
                .filter(movie -> movie.getBudget() >= lower && movie.getBudget() <= upper)
                .sorted(Collections.reverseOrder(Comparator.comparing(Movie::getRating)))
                .collect(Collectors.toList());
    }
}
