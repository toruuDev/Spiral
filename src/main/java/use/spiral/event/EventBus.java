package use.spiral.event;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("all")
public class EventBus {
    private final Map<Class<?>, List<Listener>> listeners = new HashMap<>();

    public void subscribe(Object obj) {
        for(Method method : obj.getClass().getDeclaredMethods()) { 
            if(!method.isAnnotationPresent(EventHook.class)) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes(); 
            if(params.length != 1) continue; 
            Class<?> eventType = params[0]; 
            method.setAccessible(true);
            
            listeners.computeIfAbsent(eventType, k -> new ArrayList<>()) 
            .add(new Listener(obj, method));
        }
    } 

    public void unsubscribe(Object obj) {
        for(List<Listener> group : listeners.values()) {
            group.removeIf(listener -> listener.target == obj);
        }
    } 

    public void post(Object event) {
        List<Listener> list = listeners.get(event.getClass()); 
        if(list == null) return; 

        for(Listener l : list) {
            try {
                l.method.invoke(l.target, event);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    } 

    private static class Listener {
        final Object target;
        final Method method; 
         
        Listener(Object target, Method method) {
            this.target = target;
            this.method = method;
        }
    }
}