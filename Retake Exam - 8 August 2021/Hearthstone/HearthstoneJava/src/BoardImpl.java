import java.util.*;
import java.util.stream.Collectors;

public class BoardImpl implements Board {

    private Map<String, Card> listMap;


    public BoardImpl() {
        this.listMap = new HashMap<>();

    }

    @Override
    public void draw(Card card) {
        if (contains(card.getName())) {
            throw new IllegalArgumentException();
        }

        listMap.put(card.getName(), card);
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
        if (!contains(attackerCardName) || !contains(attackedCardName)) {
            throw new IllegalArgumentException();
        }

        Card attackerCard;
        Card attackedCard;

        attackerCard = this.listMap.get(attackerCardName);
        attackedCard = this.listMap.get(attackedCardName);
        if (attackerCard == null || attackedCard == null) {
            throw new IllegalArgumentException();
        }

        if (attackerCard.getLevel() != attackedCard.getLevel()) {
            throw new IllegalArgumentException();
        }

        if (attackedCard.getHealth() <= 0) {
            return;
        }

        int damage = attackerCard.getDamage();
        int health = attackedCard.getHealth();
        if (damage >= health) {
            int score = attackerCard.getScore();
            attackerCard.setScore(score + attackedCard.getLevel());
        }
        attackedCard.setHealth(health - damage);


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
        listMap.entrySet().removeIf(c -> c.getValue().getHealth() <= 0);
    }


    @Override
    public Iterable<Card> getBestInRange(int start, int end) {

        return listMap
                .entrySet()
                .stream()
                .filter(c -> c.getValue().getScore() >= start && c.getValue().getScore() <= end)
                .sorted((o1, o2) -> o2.getValue().getLevel() - o1.getValue().getLevel())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Card> listCardsByPrefix(String prefix) {

        Comparator<Card> cardComparator = Comparator.comparing(Card::getReversedName).thenComparingInt(Card::getLevel);
        return this.listMap.values()
                .stream()
                .filter(card -> card.getName().startsWith(prefix))
                .sorted(cardComparator)
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Card> searchByLevel(int level) {

        return listMap.entrySet().stream().filter(card -> card.getValue().getLevel() == level)
                .sorted((o1, o2) -> o2.getValue().getScore() - o1.getValue().getScore())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

    }

    @Override
    public void heal(int health) {

            listMap.values().stream()
                    .min(Comparator.comparing(Card::getHealth))
                    .ifPresent(c -> c.setHealth(c.getHealth() + health));

    }

}
