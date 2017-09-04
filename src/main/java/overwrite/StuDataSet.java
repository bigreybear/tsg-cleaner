package overwrite;

import java.util.ArrayList;
import java.util.Set;

abstract public class StuDataSet implements Set<ArrayList> {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
