package validators.impl;

class ValidatorImpl {

    boolean symbolIsNotLetter(char symbol) {
        return (symbol < 'a' || symbol > 'z')
                && (symbol < 'A' || symbol > 'Z');
    }

    boolean symbolIsNotNumber(char symbol) {
        return symbol < '0' || symbol > '9';
    }
}
