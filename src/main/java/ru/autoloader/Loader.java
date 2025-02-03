package ru.autoloader;

/**
 * Класс погрузчика со свойствами
 * <b>loaderId</b><b>location</b><b>location</b>.
 *
 * @author salavatmubaraksin
 * @version 0.1
 */
public class Loader {
    private short loaderId;
    private int location;
    public boolean loaderStatus;

    public Loader(short loaderId, int location, boolean loaderStatus) {
        this.loaderId = loaderId;
        this.loaderStatus = loaderStatus;
        this.location = location;
    }

    public short getLoaderId() {
        return loaderId;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isLoaderStatus() {
        return loaderStatus;
    }

    public void setLoaderStatus(boolean loaderStatus) {
        this.loaderStatus = loaderStatus;
    }
}
