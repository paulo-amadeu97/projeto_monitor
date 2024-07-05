package factory;

import javax.persistence.Table;

public class AnnotationMapper {
    public static <T> String getTableName(Class<T> entityClass){
        if(entityClass.isAnnotationPresent(Table.class)){
            Table table = entityClass.getAnnotation(Table.class);
            return table.name();
        }
        return entityClass.getSimpleName().toLowerCase();
    }

}
