package validators.exceptions.general;

import lombok.Getter;

public class NotFoundException extends RuntimeException {
    @Getter
    private String notFoundedInfo;

    public NotFoundException(String exceptionMessage, String notFoundedInfo) {
        super(exceptionMessage);
        this.notFoundedInfo = notFoundedInfo;
    }
}
