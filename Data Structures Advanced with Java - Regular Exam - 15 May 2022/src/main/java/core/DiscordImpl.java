package core;

import models.Message;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscordImpl implements Discord {

    private Map<String, Message> listMessages;
    private List<Message> messages;

    public DiscordImpl() {
        this.listMessages = new LinkedHashMap<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public void sendMessage(Message message) {
        listMessages.put(message.getId(), message);
        messages.add(message);

    }

    @Override
    public boolean contains(Message message) {
        return messages.contains(message);
    }

    @Override
    public int size() {
        return messages.size();
    }

    @Override
    public Message getMessage(String messageId) {
        if (!listMessages.containsKey(messageId)) {
            throw new IllegalArgumentException();
        }

        return listMessages.get(messageId);
    }

    @Override
    public void deleteMessage(String messageId) {

        if (!listMessages.containsKey(messageId)) {
            throw new IllegalArgumentException();
        }

        Message message = listMessages.get(messageId);
        listMessages.remove(messageId);
        messages.remove(message);

    }

    @Override
    public void reactToMessage(String messageId, String reaction) {

        if (!listMessages.containsKey(messageId)) {
            throw new IllegalArgumentException();
        }

        Message message = listMessages.get(messageId);
        message.getReactions().add(reaction);
        Message message1 = messages.stream().filter(m -> m.getId().equals(message.getId())).findFirst().get();
        message1.getReactions().add(reaction);

    }

    @Override
    public Iterable<Message> getChannelMessages(String channel) {
        List<Message> collect = messages.stream().filter(c -> c.getChannel().equals(channel)).collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return collect;
    }

    @Override
    public Iterable<Message> getMessagesByReactions(List<String> reactions) {

        return null;
    }

    @Override
    public Iterable<Message> getMessageInTimeRange(Integer lowerBound, Integer upperBound) {
        return null;
    }

    @Override
    public Iterable<Message> getTop3MostReactedMessages() {
        return null;
    }

    @Override
    public Iterable<Message> getAllMessagesOrderedByCountOfReactionsThenByTimestampThenByLengthOfContent() {
        return null;
    }
}
