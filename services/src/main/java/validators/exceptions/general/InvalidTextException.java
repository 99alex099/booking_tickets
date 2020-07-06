package validators.exceptions.general;

import lombok.Getter;

public class InvalidTextException extends RuntimeException {
    @Getter
    private String errorText;

    public InvalidTextException(String description, String errorText) {
        super(description);
        this.errorText = errorText;
    }
    public InvalidTextException(String errorText) {
        super("incorrect text:" + errorText);
        this.errorText = errorText;
    }
}
