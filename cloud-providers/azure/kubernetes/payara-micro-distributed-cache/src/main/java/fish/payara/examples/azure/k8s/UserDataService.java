package fish.payara.examples.azure.k8s;


import fish.payara.cdi.jsr107.impl.NamedCache;

import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDataService {

    @NamedCache(cacheName = "User Dataset")
    @Inject
    private Cache<Integer, UserData> dataSet;

    @Inject
    private InstanceInfoService infoService;

    @Inject
    private CounterService counterService;

    public Optional<UserData> retrieve(Integer id) {
        return Optional.ofNullable(dataSet.get(id));
    }

    public UserData store(UserData userData) {
        Integer id = counterService.getNextValue();
        dataSet.putIfAbsent(id, new UserData(id, userData, infoService.getName()));
        return dataSet.get(id);
    }

    public List<UserData> listAll() {
        List<UserData> result = new ArrayList<>();
        dataSet.forEach(e -> result.add(e.getValue()));
        return result;
    }
}
