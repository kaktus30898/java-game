package td.Helpers;

import java.util.Collection;
import java.util.Iterator;

public class List {
    /**
     * Вспомогательная функция, достающая случайный эллемент коллекции.
     *
     * @param collection Коллекция-источник данных.
     * @param <T> Тип элементов коллекции.
     * @return Случайный элемент коллекции.
     */
    public static <T> T getRandomElement(Collection<T> collection) {
        if (collection.isEmpty())
            return null;

        final int index = (int) Math.floor(Math.random() * collection.size());

        Iterator<T> it = collection.iterator();
        for (int i = 0; i < index; i++) {
            if (it.hasNext())
                it.next();
            else
                it = collection.iterator();
        }

        return it.hasNext() ? it.next() : null;
    }
}
