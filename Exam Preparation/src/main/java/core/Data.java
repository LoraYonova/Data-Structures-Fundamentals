package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Data implements Repository {
    private List<Entity> data;

    public Data() {
        this.data = new ArrayList<>();
    }

    public Data(Data other) {
        this.data = new ArrayList<>(other.data);
    }

    @Override
    public void add(Entity entity) {
        this.data.add(entity);
    }

    @Override
    public Entity getById(int id) {

        for (int i = 0; i < data.size(); i++) {
            if (this.data.get(i).getId() == id) {
                return this.data.get(i);
            }
        }

        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        List<Entity> result = new ArrayList<>();

        for (int i = 0; i < this.data.size(); i++) {
            if (this.data.get(i).getParentId() == id) {
                result.add(this.data.get(i));
            }
        }
        return result;
    }

    @Override
    public List<Entity> getAll() {
        return this.data;
    }

    @Override
    public Repository copy() {

        return new Data(this);
  }

    @Override
    public List<Entity> getAllByType(String type) {
        List<Entity> collect = this.data.stream().filter(entity -> entity.getClass().getSimpleName().equals(type)).collect(Collectors.toList());

        Entity entity1 = this.data.stream().filter(entity -> !entity.getClass().getSimpleName().equals(type)).findFirst().orElse(null);

        if (entity1 == null) {
            throw new IllegalArgumentException("Illegal type: " + type);

        }

        return collect;
    }

    @Override
    public Entity peekMostRecent() {
        int size = this.data.size();

        if (size == 0) {
            throw new IllegalStateException("Operation on empty Data");
        }

        return this.data.get(size - 1);

    }

    @Override
    public Entity pollMostRecent() {
        int mostRecentId = this.data.size();

        if (mostRecentId == 0) {
            throw new IllegalStateException("Operation on empty Data");
        }

        Entity entity = this.data.get(mostRecentId - 1);
        this.data.remove(entity);

        return entity;
    }

    @Override
    public int size() {
        return this.data.size();
    }
}
