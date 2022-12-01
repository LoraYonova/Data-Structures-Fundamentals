package couponsopsjava;

public class Website {

    public String domain;
    public int usersCount;

    public Website(String domain, int usersCount) {
        this.domain = domain;
        this.usersCount = usersCount;
    }

    public String getDomain() {
        return domain;
    }

    public Website setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public Website setUsersCount(int usersCount) {
        this.usersCount = usersCount;
        return this;
    }
}
