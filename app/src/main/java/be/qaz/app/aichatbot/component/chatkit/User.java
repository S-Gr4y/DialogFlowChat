package be.qaz.app.aichatbot.component.chatkit;

import com.stfalcon.chatkit.commons.models.IUser;

import java.util.UUID;

/*
 * Created by troy379 on 04.04.17.
 */
public class User implements IUser {

    private String id;
    private String name;
    private String avatar;
    private boolean online;

    public User(String id, String name, String avatar, boolean online) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.online = online;
    }

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.avatar = "";
        this.online = true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public boolean isOnline() {
        return online;
    }
}
