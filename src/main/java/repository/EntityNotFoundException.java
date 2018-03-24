package repository;

public class EntityNotFoundException extends Exception {

    private Long entityID;
    private String entityClass;

    public EntityNotFoundException(Long entityID, String entityClass) {
        super(entityClass + "with id " + entityID + " not found!");
        this.entityClass = entityClass;
        this.entityID = entityID;
    }
}
