package core;

import interfaces.Buffer;
import interfaces.Entity;
import model.BaseEntity;

import java.util.*;
import java.util.stream.Collectors;

public class Loader implements Buffer {

    private List<Entity> entities;

    public Loader() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }

    @Override
    public Entity extract(int id) {
        Entity entity = null;

        for (Entity entity1 : entities) {
            if (entity1.getId() == id) {
                entity = entity1;
                break;
            }
        }

        if (entity != null) {
            this.entities.remove(entity);
        }

        return entity;
    }

    @Override
    public Entity find(Entity entity) {

        for (Entity entity1 : entities) {
            if (entity1.equals(entity)) {
                return entity1;
            }
        }
        return null;
    }

    @Override
    public boolean contains(Entity entity) {
        return this.entities.contains(entity);
    }

    @Override
    public int entitiesCount() {
        return this.entities.size();
    }

    @Override
    public void replace(Entity oldEntity, Entity newEntity) {

        int firstIndex = entities.indexOf(oldEntity);


        if (firstIndex < 0) {
            throw new IllegalStateException("Entity not found");
        }

        Collections.replaceAll(this.entities, oldEntity, newEntity);

    }

    @Override
    public void swap(Entity first, Entity second) {

        int firstIndex = entities.indexOf(first);
        int secondIndex = entities.indexOf(second);

        if (firstIndex < 0 || secondIndex < 0) {
            throw new IllegalStateException("Entities not found");
        }

        Collections.swap(this.entities, firstIndex, secondIndex);
    }

    @Override
    public void clear() {
        this.entities.clear();
    }

    @Override
    public Entity[] toArray() {
        return this.entities.toArray(Entity[]::new);
    }

    @Override
    public List<Entity> retainAllFromTo(BaseEntity.Status lowerBound, BaseEntity.Status upperBound) {


        int first = lowerBound.ordinal();
        int last = upperBound.ordinal();

        List<Entity> result = new ArrayList<>();

        for (int i = 0; i < this.entities.size(); i++) {
            Entity entity = entities.get(i);
            int status = entity.getStatus().ordinal();

            if (last >= status && first <= status) {
                result.add(this.entities.get(i));
            }
        }

        return result;

    }

    @Override
    public List<Entity> getAll() {
        return entities;
    }

    @Override
    public void updateAll(BaseEntity.Status oldStatus, BaseEntity.Status newStatus) {

        for (int i = 0; i < this.entities.size(); i++) {
            Entity entity = this.entities.get(i);
            BaseEntity.Status status = entity.getStatus();
            if (status.equals(oldStatus)) {
                entity.setStatus(newStatus);
            }
        }
    }

    @Override
    public void removeSold() {

        for (int i = 0; i < this.entities.size(); i++) {
            Entity entity = this.entities.get(i);
            BaseEntity.Status status = entity.getStatus();
            if (BaseEntity.Status.SOLD.equals(status)) {
                this.entities.remove(entity);
            }
        }

    }

    @Override
    public Iterator<Entity> iterator() {
        return new Iterator<Entity>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < entities.size();
            }

            @Override
            public Entity next() {
                return getAll().get(index++);
            }
        };
    }
}
