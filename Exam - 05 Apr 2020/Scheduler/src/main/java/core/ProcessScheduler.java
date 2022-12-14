package core;

import model.Task;
import shared.Scheduler;

import java.util.*;

public class ProcessScheduler implements Scheduler {

    private Deque<Task> tasks;

    public ProcessScheduler() {
        this.tasks = new ArrayDeque<>();
    }


    @Override
    public void add(Task task) {
       tasks.offer(task);
    }

    @Override
    public Task process() {
        return tasks.poll();
    }

    @Override
    public Task peek() {
        return tasks.peek();
    }

    @Override
    public Boolean contains(Task task) {
        return tasks.contains(task);
    }

    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public Boolean remove(Task task) {
        if (tasks.remove(task)) {
            return true;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Boolean remove(int id) {
        Task task = tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        if (task != null) {
            return tasks.remove(task);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void insertBefore(int id, Task task) {
        List<Task> buffer = new ArrayList<>();
        Task current = tasks.peek();
        while (current != null && current.getId() != id) {
            buffer.add(tasks.poll());
            current = tasks.peek();
        }

        if (current == null) {
            throw new IllegalArgumentException();
        }

        buffer.add(task);

        while (!buffer.isEmpty()) {
            this.tasks.addFirst(buffer.remove(buffer.size() - 1));
        }
    }

    @Override
    public void insertAfter(int id, Task task) {
        List<Task> buffer = new ArrayList<>();
        Task current = tasks.peek();
        while (current != null && current.getId() != id) {
            buffer.add(tasks.poll());
            current = tasks.peek();
        }

        if (current == null) {
            throw new IllegalArgumentException();
        } else {
            buffer.add(tasks.poll());
        }

        buffer.add(task);

        while (!buffer.isEmpty()) {
            this.tasks.addFirst(buffer.remove(buffer.size() - 1));
        }
    }

    @Override
    public void clear() {
        tasks.clear();
    }

    @Override
    public Task[] toArray() {
        Task[] tasks = new Task[this.size()];
        return this.tasks.toArray(tasks);
    }

    @Override
    public void reschedule(Task first, Task second) {
        List<Task> tasks = toList();

        int firstIndex = tasks.indexOf(first);
        int secondIndex = tasks.indexOf(second);

        if (firstIndex == -1 || secondIndex == -1) {
            throw new IllegalArgumentException();
        }

        Collections.swap(tasks, firstIndex, secondIndex);

        this.tasks = new ArrayDeque<>(tasks);
    }

    @Override
    public List<Task> toList() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void reverse() {
        List<Task> all = toList();
        Collections.reverse(all);
        this.tasks = new ArrayDeque<>(all);
    }

    @Override
    public Task find(int id) {
        Task task = tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);

        if (task == null) {
            throw new IllegalArgumentException();
        }

        return task;
    }

    @Override
    public Task find(Task task) {
      return find(task.getId());
    }
}
