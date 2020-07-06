package validators.interfaces;

public interface Validator<Entity> {
    void isValid(Entity entity);
}
