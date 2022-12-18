package org.softuni.exam.structures;

import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class ViTubeRepositoryImpl implements ViTubeRepository {

    private List<User> users;
    private List<Video> videos;
    private List<User> passiveUser;
    private Map<User, List<Video>> viTube;

    public ViTubeRepositoryImpl() {
        this.users = new ArrayList<>();
        this.videos = new ArrayList<>();
        this.passiveUser = new ArrayList<>();
        this.viTube = new LinkedHashMap<>();
    }

    @Override
    public void registerUser(User user) {

        users.add(user);
        passiveUser.add(user);

    }

    @Override
    public void postVideo(Video video) {
        videos.add(video);
    }

    @Override
    public boolean contains(User user) {
        return users.contains(user);
    }

    @Override
    public boolean contains(Video video) {
        return videos.contains(video);
    }

    @Override
    public Iterable<Video> getVideos() {
        return videos;
    }

    @Override
    public void watchVideo(User user, Video video) throws IllegalArgumentException {

        if (!users.contains(user) || !videos.contains(video)) {
            throw new IllegalArgumentException();
        }

        int views = video.getViews();
        video.setViews(views + 1);
        passiveUser.remove(user);
        viTube.putIfAbsent(user, List.of(video));

    }

    @Override
    public void likeVideo(User user, Video video) throws IllegalArgumentException {

        if (!users.contains(user) || !videos.contains(video)) {
            throw new IllegalArgumentException();
        }

        int likes = video.getLikes();
        video.setLikes(likes + 1);
        passiveUser.remove(user);
        viTube.putIfAbsent(user, List.of(video));

    }

    @Override
    public void dislikeVideo(User user, Video video) throws IllegalArgumentException {

        if (!users.contains(user) || !videos.contains(video)) {
            throw new IllegalArgumentException();
        }

        int dislikes = video.getDislikes();
        video.setDislikes(dislikes + 1);
        passiveUser.remove(user);
        viTube.putIfAbsent(user, List.of(video));
    }

    @Override
    public Iterable<User> getPassiveUsers() {
        return passiveUser;
    }

    @Override
    public Iterable<Video> getVideosOrderedByViewsThenByLikesThenByDislikes() {

        return videos
                .stream()
                .sorted((o1, o2) -> {
                    if (o2.getViews() - o1.getViews() == 0) {
                        if (o2.getLikes() - o1.getLikes() == 0) {
                            return o1.getDislikes() - o2.getDislikes();
                        }
                        return o2.getLikes() - o1.getLikes();
                    }
                    return o2.getViews() - o1.getViews();


                }).collect(Collectors.toList());

    }

    @Override
    public Iterable<User> getUsersByActivityThenByName() {

     return viTube.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    Video video1 = videos.get(o1.getValue().size());
                    Video video2 = videos.get(o2.getValue().size());

                    if (video2.getViews() - video1.getViews() == 0) {
                        if (video2.getLikes() - video1.getLikes() == 0) {
                            if (video2.getDislikes() - video1.getDislikes() == 0) {
                                return o1.getKey().getUsername().compareTo(o2.getKey().getUsername());
                            }
                            return video2.getDislikes() - video1.getDislikes();
                        }
                        return video2.getLikes() - video1.getLikes();
                    }

                        return video2.getViews() - video1.getViews();
                }).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
