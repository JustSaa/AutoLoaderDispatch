package ru.autoloader;

import java.util.ArrayList;
import java.util.List;

public class LoaderManager {
    private static short nextLoaderId = 1;
    private List<Loader> loaders;

    public LoaderManager() {
        loaders = new ArrayList<>();
    }

    public void addLoader(int location, boolean loaderStatus) {
        Loader newLoader = new Loader(nextLoaderId++, location, loaderStatus);
        loaders.add(newLoader);
    }

    public List<Loader> getLoaders() {
        return loaders;
    }

    public Loader getLoaderById(short loaderId) {
        for (Loader loader : loaders) {
            if (loader.getLoaderId() == loaderId) {
                return loader;
            }
        }
        return null;
    }

    public void updateLoaderStatus(short loaderId, boolean newStatus) {
        Loader loader = getLoaderById(loaderId);
        if (loader != null) {
            loader.setLoaderStatus(newStatus);
        }
    }
}