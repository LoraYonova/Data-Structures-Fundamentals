import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardImpl implements Board {

    private Map<String, List<Card>> listMap;
    private List<Card> cards;

    public BoardImpl() {
        this.listMap = new HashMap<>();
        this.cards = new ArrayList<>();
    }

    @Override
    public void draw(Card card) {
       if (contains(card.getName())) {
           throw new IllegalArgumentException();
       }

       listMap.put(card.getName(), List.of(card));
    }

    @Override
    public Boolean contains(String name) {
       return listMap.containsKey(name);
    }

    @Override
    public int count() {
        return listMap.size();
    }

    @Override
    public void play(String attackerCardName, String attackedCardName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(String name) {
       if (!contains(name)) {
           throw new IllegalArgumentException();
       }

       listMap.remove(name);
    }

    @Override
    public void removeDeath() {
        List<Card> collect = new ArrayList<>();
        for (List<Card> value : listMap.values()) {
            collect = value.stream().filter(c -> c.getHealth() <= 0).collect(Collectors.toList());
        }

        listMap.values().remove(collect);
    }

    @Override
    public Iterable<Card> getBestInRange(int start, int end) {
        List<Card> collect = new ArrayList<>();
        for (List<Card> value : listMap.values()) {
             collect = value.stream().filter(c -> c.getScore() >= start && c.getScore() <= end).sorted((o1, o2) -> {
                int result = o2.getLevel() - o1.getLevel();
                if (result == 0) {
                    result = o1.getLevel() - o2.getLevel();
                }
                return result;
            }).collect(Collectors.toList());
        }

        return collect;
    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Card> searchByLevel(int level) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void heal(int health) {
        throw new UnsupportedOperationException();
    }
}
